#############################
# Authors: Joshua Cullings  #
#          Daniel Laden     #
#                           #
# Date: 9/10/2019           #
#############################

import numpy as np
import sys

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
        V[i][0] = -i #initializing the outer edges of the matrix

    for j in range(0,Tlen):
        V[0][j] = -j #initializing the outer edges of the matrix
    
    return V

def scoring(s,t): #checks if the characters match and returns the resulting value
    if(s == t):
        return match
    else:
        return mismatch

def fillMatr(S,T,V):
    Slen = len(S)
    Tlen = len(T)
    
    for i in range(1,Slen):
        for j in range(1,Tlen):
            m = int(V[i-1][j-1] + scoring(S[i],T[j]))
            d = int(V[i-1][j] + inDel)
            ins = int(V[i][j-1] + inDel)
            
            result = max(m,d,ins)

            V[i][j] = result
            
    print("Optimal Score: "+str(V[Slen-1][Tlen-1])+"\n")
    
    return V

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
            
#From assignment 1
#Added fasta file
if len(sys.argv) > 1: #if command line input exists perform neccessary actions
    fasta_file = open(sys.argv[1], 'r')

    try:
        match = int(sys.argv[2])
        mismatch = int(sys.argv[3])
        inDel = int(sys.argv[4])

    except:
        print("Incorrect input")
    
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

    V = prepMatr(sequences[0][1],sequences[1][1])

    filledV = fillMatr(" "+sequences[0][1]," "+sequences[1][1],V) #Fills the matrix according to sent in values for match, mismatch, and insert/delete

    alignedS, alignedT = globalAlign(" "+sequences[0][1]," "+sequences[1][1],V)

    print("Alligned strings:")
    print(alignedS)
    print(alignedT)
    
else:
    #no fasta file end program
    quit()

#########################################################################
# References:                                                           #
#   https://en.wikipedia.org/wiki/Needleman%E2%80%93Wunsch_algorithm    #
#########################################################################
