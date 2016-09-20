import random

file = "inputdata.csv"
f = open(file,'w')

f.write('ID'+','+'AGE'+','+'BodyMassIndex'+','
    +'Education'+','+'Profession'+','
    +'AnnualIncome'+','+'InsuranceAwareness'+'\n')

for x in range(100000):
    f.write(str(x+1)+','+str(random.choice([1,2,3,4,5]))+','+str(random.choice([1,2,3,4,5]))+','
        +str(random.choice([1,2,3,4,5,6,7]))+','+str(random.choice([1,2,3,4,5,6,7,8,9,10]))+','
        +str(random.choice([1,2,3,4,5,6]))+','+str(random.choice([1,2,3,4,5,6,7,8,9,10]))+'\n')

f.close()


## 1, 2, 3, 4, 5, 6
