#################################
# Author(s): Joshua Cullings    #
#            Daniel Laden       #
#                               #
# Date: 11/4/2019               #
#################################

import numpy as np
import sys

#########################################################################################
# The following alignment code was primarily pulled from our Assignment 2 submission.   #
# We did make some alterations to make it work with the Center-Star Algorithm.          #
#########################################################################################

match = 0
mismatch = 0
inDel = 0

def printMatr(V): #prints the matrix passed in without commas, or brackets
    for i in range(0,len(V)):
        print(str(V[i]).replace("[","").replace("]","").replace(",",""),)
        
def prepMatr(S, T):
    Slen = len(S)+1
    Tlen = len(T)+1
    
    V = [[0]*Tlen for i in range(Slen)] #initialize the matrix size |F| by |T|

    for i in range(0,Slen):
        V[i][0] = i #initializing the outer edges of the matrix

    for j in range(0,Tlen):
        V[0][j] = j #initializing the outer edges of the matrix
    
    return V

def scoring(s,t): #checks if the characters match and returns the resulting value
    if(s == t):
        return match
    else:
        return mismatch

def setMismatch_InDel(mis, ID):
    mismatch = mis
    inDel = ID

def fillMatr(S,T,V):
    Slen = len(S)
    Tlen = len(T)
    
    for i in range(1,Slen):
        for j in range(1,Tlen):
            m = int(V[i-1][j-1] + scoring(S[i],T[j]))
            d = int(V[i-1][j] + inDel)
            ins = int(V[i][j-1] + inDel)
            
            result = min(m,d,ins)

            V[i][j] = result
    optimal = V[Slen-1][Tlen-1]
    #print("Optimal Score: "+str(optimal)+"\n")
    
    return V,optimal

def globalAlign(S,T,V):
    alignedS = ""
    alignedT = ""
    i = len(S)-1
    j = len(T)-1

    while(i>0 and j>0):
        if(i>0 and j>0 and V[i][j] == V[i][j] + scoring(S[i],T[j])):
            alignedS = S[i] + alignedS
            alignedT = T[j] + alignedT
            i -= 1
            j -= 1
          
        elif(i>0 and V[i][j] == V[i-1][j] + inDel):
            alignedS = S[i] + alignedS
            alignedT = "-" + alignedT
            i -= 1

        else:
           alignedS = "-" + alignedS
           alignedT = T[j] + alignedT
           j -= 1
           
    return alignedS, alignedT


def distance(sequences):
    distances = []
    print("\nD(Si,Sj) Matrix")
    for i in range(len(sequences)):
        dist = 0
        print(sequences[i])
        for j in range(len(sequences)):
            dist += sequences[i][j]
        distances.append(dist)
        
    return distances

def centerStar(sequences):
    n = len(sequences)
    D_all = [[0]*n for i in range(n)]
    alignments = {}
    for i in range(n):
        for j in range(n):
            V = prepMatr(sequences[i][1],sequences[j][1])
            _, optimal = fillMatr(" "+sequences[i][1]," "+sequences[j][1],V) #Fills the matrix according to sent in values for match, mismatch, and insert/delete
            D_all[i][j] = optimal
            #_, alignments[i].append(globalAlign(" "+sequences[0][1]," "+sequences[1][1],V))

    distances = distance(D_all)
    center = distances.index(min(distances))
    print("\nSummation of Alignment Scores:")
    for row in distances:
        print(row)
    print("\nThe center string is %s" % sequences[center][1])
    print("with a score of %d" % distances[center])
    

#From assignment 1
#Added fasta file
if len(sys.argv) > 1: #if command line input exists perform neccessary actions
    fasta_file = open(sys.argv[1], 'r')
    try:
        alpha = int(sys.argv[2])
        beta = int(sys.argv[3])
        if (alpha <= 0 or beta <= 0):
            print("alpha and beta must be positive integer values.")
            quit()
            
        mismatch = alpha
        inDel = beta
    except:
        print("Invalid input")
        quit()
            
    seq_holder = ["",""]
    sequences = []
    
    for line in fasta_file:
        if not seq_holder[0]:
            seq_holder[0] = line.replace(">","")

        else:
            seq_holder[1] = line.replace("\n","")
            #finished sequence
            sequences.append(tuple(seq_holder)) #changing from a list to a tuple so there's no way to change the data
            seq_holder = ["",""] #resetting seq_holder for the next sequence

    centerStar(sequences)
    
else:
    quit()

#################################################################################
# References:                                                                   #
#   - Algorithms in Bioinformatics: A Practical Introduction (Chapman & Hall)   #
#################################################################################
