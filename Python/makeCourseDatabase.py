import json
import getSingleCourse

with open('courses.json') as f:
	data = json.loads(f.read())

courses = data['course']

# Be careful when running the following  of code
#for i in range len(courses) in courses:
#	c = json.loads(getSingleCourse.getCourseData(courses[i]['code']))
#	c = c['course']

c = json.loads(getSingleCourse.getCourseData(courses[0]['code']))
c = c['course']


tempDB = dict()
tempDB['code'] = dict()
tempDB['code']['noName'] = c['englishName']
tempDB['code']['enName'] = c['norwegianName']
tempDB['code']['subArea'] = []
for subjectArea in c['subjectArea']:
	tempDB['code']['subArea'].append(subjectArea['code'])


with open('data.json', 'w') as outfile:
	json.dump(tempDB, outfile)