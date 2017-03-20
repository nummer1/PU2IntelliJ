# gets information from http://www.ime.ntnu.no/api/ about all coursenames 
# stored in file 'courses.json' and saves them to data.json in JSON format

import json
import getSingleCourse

with open('courses.json') as f:
	data = json.loads(f.read())

courses = data['course']

#print(courses[3380]['code'])
#getSingleCourse(courses[i]['code]'])

tempDB = dict()
# Be careful when running the following  of code
for i in range(len(courses)):
	if i != 3380:
		c = json.loads(getSingleCourse.getCourseData(courses[i]['code']))
		c = c['course']
		tempDB[courses[i]['code']] = c
		print(i)

print(tempDB.keys())

with open('data.json', 'w') as outfile:
	json.dump(tempDB, outfile)