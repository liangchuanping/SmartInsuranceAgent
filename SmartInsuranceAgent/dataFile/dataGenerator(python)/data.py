import random

file = "dataset.csv"
f = open(file,'w')

f.write('index'+','+'Age'+','+'BodyMassIndex'+','
	+'Education'+','+'Profession'+','
	+'AnnualIncome'+','+'InsuranceAwareness'+'\n')

for x in range(2500):
	f.write('1'+','+str(random.choice([2,3]))+','+str(random.choice([2,3]))+','
		+str(random.choice([5,6,7]))+','+str(random.choice([3,4,5,6,7]))+','
		+str(random.choice([2,3,4]))+','+str(random.choice([4,5,6,7,8,9,10]))+','+str(random.choice([1,2,3,4,5,6,7,8,9,10]))+'\n')

for x in range(200):
	f.write('1'+','+str(random.choice([1,4,5]))+','+str(random.choice([1,4,5]))+','
		+str(random.choice([1,2,3,4]))+','+str(random.choice([1,2,8,9,10]))+','
		+str(random.choice([1,5,6]))+','+str(random.choice([1,2,3]))+','+str(random.choice([1,2,3,4,5,6,7,8,9,10]))+'\n')

for x in range(200):
	f.write('0'+','+str(random.choice([2,3]))+','+str(random.choice([2,3]))+','
		+str(random.choice([5,6,7]))+','+str(random.choice([3,4,5,6,7]))+','
		+str(random.choice([2,3,4]))+','+str(random.choice([4,5,6,7,8,9,10]))+','+str(random.choice([1,2,3,4,5,6,7,8,9,10]))+'\n')
		
for x in range(5000):
	f.write('0'+','+str(random.choice([1,4,5]))+','+str(random.choice([1,4,5]))+','
		+str(random.choice([1,2,3,4]))+','+str(random.choice([1,2,8,9,10]))+','
		+str(random.choice([1,5,6]))+','+str(random.choice([1,2,3]))+','+str(random.choice([1,2,3,4,5,6,7,8,9,10]))+'\n')	
	
f.close()


## 1, 2, 3, 4, 5, 6
