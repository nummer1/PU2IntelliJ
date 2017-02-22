import urllib.request as url

urlapi = 'http://www.ime.ntnu.no/api/'

urlCourses = 'course/-'

# return list of (norwegian) courses
listCourses = url.urlopen(urlapi + urlCourses).read().decode()

f = open('courses.json', 'w')
f.write(listCourses)
f.close()

#everything is in Json format
#NTNUapi returns courses in form dict_keys(['course', 'request', 'cache'])
#'course' is list, with elements dict_keys(['name', 'code', 'englishName', 'norwegianName', 'versionCode'])
#'request' is dict_keys(['year', 'language', 'courseCode', 'term'])
#'cache' is dict_keys(['updated', 'expires'])