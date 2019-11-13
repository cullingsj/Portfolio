################################
# Categorical Variable Counter #
################################
# Author: Josh Cullings        #
#                              #
# Date: 10/23/2019             #
################################

import csv

def partitionData(filename):
    catCounts = {}
    
    with open(f'{filename}') as csvfileIn:
            fileIn = csv.reader(csvfileIn, delimiter=',', quotechar='|')
            data = list(fileIn)
            
            for dataEntry in data:
                # Change this section based on file you're reading in
                for i in range(len(dataEntry) - 1):
                    if(not i in catCounts.keys()):
                        catCounts[i] = {}
                        catCounts[i][dataEntry[i]] = 1

                    elif(dataEntry[i] in catCounts[i]):
                        catCounts[i][dataEntry[i]] += 1
                    else:
                        catCounts[i][dataEntry[i]] = 1

    return catCounts


counts = partitionData("machine.data")
for i in range(len(counts)):
    nuerons = 0
    for j in counts[i]:
        print("\""+str(j)+"\",", end = '')
        nuerons += 1
        
    print("\n\nFeature "+str(i+1)+" contains "+str(nuerons)+" neurons.\n")
