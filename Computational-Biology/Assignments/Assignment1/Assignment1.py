############################
# Authors: Joshua Cullings #
#          Daniel Laden    #
#                          #
# Date: 9/10/2019          #
############################
import numpy as np
import sys

def zAlgo(P, S):
    concatString=P+'$'+S
    n=len(concatString)
    Z=[None]*n
    Z[0]='x'
    L,R,=0,0

    for i in range(1,n):
        if (i > R):
            L=R=i
            while(R<n and concatString[R-L] == concatString[R]):
                R+=1
            Z[i]=R-L
            R-=1
        else:
            k=i-L
            if(Z[k]<(R-i+1)):
                Z[i]=Z[k]
            else:
                L=i
                while(R<n and concatString[R-L] == concatString[R]):
                    R+=1
                Z[i]=R-L
                R-=1
    print(Z)

#Test for zAlgo by itself
zAlgo("aab", "baabaabaabaa")
zAlgo("crow", "the crow doesn't like other crows just fruit")
zAlgo("ATG", "ACTTGTCATGGTAACTCCGTCGTACCAGTAGGTCATG")

#Added fasta file
if len(sys.argv) > 1: #if command line input exists perform neccessary actions
    pattern = sys.argv[1]
    fasta_file = open(sys.argv[2], 'r')

    seq_holder = ["",""]
    sequences = []
    for line in fasta_file:
        if not seq_holder[0]:
            seq_holder[0] = line.replace(">","")
        else:
            seq_holder[1] = line
            #finished sequence
            sequences.append(tuple(seq_holder)) #changing from a list to a tuple so there's no way to change the data
            seq_holder = ["",""] #resetting seq_holder for the next sequence

    #we should have all the sequences from the fasta file now, time to find the user entered pattern
    for seq in sequences:
        print("Z-algorithm test on "+seq[0]+" with pattern "+pattern)
        zAlgo(pattern,seq[1])

else:
    #no fasta file end program
    quit()

####################################################
#Code References
#
#https://www.journaldev.com/23674/python-remove-character-from-string
#https://www.geeksforgeeks.org/python-convert-a-list-into-a-tuple/
#https://stackoverflow.com/a/5423400
#http://prodata.swmed.edu/promals/info/fasta_format_file_example.htm
####################################################
