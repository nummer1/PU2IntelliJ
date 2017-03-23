#MTDT

UPDATE CourseStudyProgram SET MandatoryString = 'O' WHERE StudyCode = 'MTDT' AND CourseCode IN('TDT4110', 'TMA4100', 'TMA4140' 'EXPH0004', 'TDT4100', 'TDT4112', 'TFE4101', 'TMA4115', 'TDT4113', 'TDT4120', 'TDT4160', 'TMA4240', 'TDT4140', 'TDT4145', 'TDT4180', 'TTM4100', 'TDT4136', 'TMA4135', 'TFY4125', 'TDT4186', 'TIØ4258', 'TDT4290', 'TDT4900', 'TDT4501', 'TDT4506', 'TDT4900');
UPDATE CourseStudyProgram SET MandatoryString = 'VA' WHERE StudyCode = 'MTDT' AND CourseCode IN ('TDT4165', 'TDT4195', 'TDT4117', 'TDT4137', 'TDT4175', 'TDT4125', 'TDT4205', 'TDT4258', 'TDT4186', 'TDT4171', 'TDT4240', 'TDT4300', 'IT3105', 'TDT4173', 'TDT4205', 'TDT4230', 'TDT4260', 'TDT4173', 'TDT4205', 'TDT4230', 'TDT4260', 'TDT4265', 'TDT4305');
UPDATE CourseStudyProgram SET MandatoryString = 'VB' WHERE StudyCode = 'MTDT' AND CourseCode IN('TIØ4120', 'TIØ4146', 'TIØ4230', 'TIØ5200', 'TMM4225', 'TPD4142', 'TTM4165', 'IT3708', 'TDT4230', 'TDT4230', 'TMA4105', 'TMA4280', 'TTT4110', 'MOL4010', 'TET4120', 'TFE4160', 'TFY4195', 'IT3010', 'IT3708', 'TDT4150', 'TDT4171', 'TDT4210', 'TDT4215', 'TDT4240', 'TDT4242', 'TDT4245', 'TDT4280', 'TDT4300', 'TET4120', 'TFE4160', 'TFY4195', 'TFY4220', 'TMA4105', 'TMA4235', 'TMA4280', 'TTK4130', 'TTK4135', 'TTK4165', 'TTK4175', 'TTM4115', 'TTM4135', 'TTM4185', 'TTT4200', 'TTT4225', 'TIØ4120', 'TIØ4146', 'TIØ4230', 'TIØ5200', 'TMM4220', 'TMM4225', 'TPD4142', 'TTM4165');
UPDATE CourseStudyProgram SET MandatoryString = 'V' WHERE StudyCode = 'MTDT' AND CourseCode IN('AAR4911', 'BI2096', 'BI2097', 'BI2098', 'EIT3006', 'EIT3014', 'HLD3800', 'KJ2095', 'MFEL4954', 'PSY3808');
UPDATE CourseStudyProgram SET Semester = 1 WHERE StudyCode = 'MTDT' AND CourseCode IN('EXPH0004', 'TDT4110', 'TMA4100', 'TMA4140');
UPDATE CourseStudyProgram SET Semester = 2 WHERE StudyCode = 'MTDT' AND CourseCode IN('TDT4100', 'TDT4112', 'TFE4101', 'TMA4115');
UPDATE CourseStudyProgram SET Semester = 3 WHERE StudyCode = 'MTDT' AND CourseCode IN('TDT4113', 'TDT4120', 'TDT4160', 'TMA4240');
UPDATE CourseStudyProgram SET Semester = 4 WHERE StudyCode = 'MTDT' AND CourseCode IN('TDT4140', 'TDT4145', 'TDT4180', 'TTM4100');

DELETE FROM CourseStudyProgram WHERE StudyCode = 'MTDT' AND CourseCode IN('HMS0002', 'TFY4125', 'TDT4102', 'TTM4185', 'MOL4010');