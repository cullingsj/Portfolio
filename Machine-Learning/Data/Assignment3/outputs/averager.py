import csv
import sys
import statistics

def partitionData(filename):
    catCounts = {}
    print("\n----------------------------------------------\n"+
          "Attempting to open "+filename+
          "\n----------------------------------------------\n")
    with open(f'{filename}') as csvfileIn:
        fileIn = csv.reader(csvfileIn, delimiter=',', quotechar='|')
        data = list(fileIn)
        return data    
        '''
        print(data)
            
        for dataEntry in data:
            print(dataEntry)
            # Change this section based on file you're reading in
            catCounts = dataEntry
            for i in range(len(dataEntry) - 1):
                if(not i in catCounts.keys()):
                    catCounts[i] = {}
                    catCounts[i][dataEntry[i]] = 1

                elif(dataEntry[i] in catCounts[i]):
                    catCounts[i][dataEntry[i]] += 1
                else:
                    catCounts[i][dataEntry[i]] = 1

    return catCounts'''

if len(sys.argv) > 1:
    counts = partitionData(str(sys.argv[1])+'.csv')
    fileIndex = 2
    averages={}
    for i in range(1,len(counts[0])):
        averages[i] = 0
        
    while(True):
        for j in range(3,len(counts[0])):
            numEntries = 0
            average = 0
            for i in range(1,len(counts)):
                if(not counts[i][j]=='NaN'):
                    average += float(counts[i][j])
                    numEntries += 1

            if(numEntries > 0):
                average = average/numEntries
                
            averages[j] += average
            print("\n"+str(counts[0][j])+" averages "+str(average)+"\n")
        try:
            #print("Attempting to open "+str(sys.argv[1])+'-'+str(fileIndex)+'.csv')
            counts = partitionData(str(sys.argv[1])+'-'+str(fileIndex)+'.csv')
            fileIndex += 1
        except:
            print("Failed")
            for j in range(1,len(counts[0])):
                print("\n Total "+str(counts[0][j])+" averages "+str(averages[j]/(fileIndex-1))+"\n")
            break
