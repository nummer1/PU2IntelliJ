import requests

urlapi = 'http://www.ime.ntnu.no/api/'

urlCourse = 'course/'

#returns (norwegian) course with courseCode in json format
def getCourseData(courseCode):
	course = requests.get(urlapi + urlCourse + courseCode)

	return course.text

#everything is in Json format
#NTNUapi returns single course in form dict_keys(['course', 'request', 'cache'])
#'request' is dict_keys(['year', 'language', 'courseCode', 'term'])
#'cache' is dict_keys(['updated', 'expires'])
#'course is dict_keys(['courseTypeCode', 'usedInStudyprogrammes', 'subjectArea', 'taughtInEnglish', 
#	'code', 'gradeRuleText', 'studyLevelCode', 'courseTypeName', 'studyLevelName', 'lastYearTaught', 
#	'name', 'lastAssessmentYear', 'assessment', 'norwegianName', 'educationalRole', 'creditTypeName', 
#	'registrationAfterAdmission', 'educationLanguage', 'taughtInSpring', 'englishName', 'studyLevel', 
#	'credit', 'versionCode', 'taughtFromTerm', 'taughtInAutumn', 'location', 'creditReduction', 'taughtFromYear', 
#	'educationTerm', 'infoType', 'studyProgrammeCode', 'gradeRule', 'admissionRequirement', 'creditTypeCode', 'ouId'])
#Some of the keys will give you a list with an arbritary number of dictionaries