---Donia : add HR and Employe module 

SET IDENTITY_INSERT [Master].[MasterModule] ON
INSERT INTO [Master].[MasterModule] ([Id], [Code]) VALUES (37, N'HR_SPACE')
INSERT INTO [Master].[MasterModule] ([Id], [Code]) VALUES (38, N'EMPLOYEE_SPACE')
SET IDENTITY_INSERT [Master].[MasterModule] OFF

--- Donia: Update HR and Employee mastersubmodule 
SET IDENTITY_INSERT [Master].[MasterSubModule] ON
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'EMPLOYEES'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'CONTRACT'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'LEAVE_EMPLOYEE'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'ORGANIZATIONAL'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'TEAMS'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'SKILLS_MATRIX'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'ANNUAL_INTERVIEWS'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'HR_SPACE' ) WHERE Code = 'INTERVIEW'

UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'EMPLOYEE_SPACE' ) WHERE Code = 'TIMESHEET'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'EMPLOYEE_SPACE' ) WHERE Code = 'LEAVE'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'EMPLOYEE_SPACE' ) WHERE Code = 'LEAVE_REMAINING_BALANCE'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'EMPLOYEE_SPACE' ) WHERE Code = 'EXPENSE_REPORT'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'EMPLOYEE_SPACE' ) WHERE Code = 'DOCUMENTS'
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'EMPLOYEE_SPACE' ) WHERE Code = 'SHARED_DOCUMENT'

SET IDENTITY_INSERT [Master].[MasterSubModule] OFF

--- Donia: Delete module
SET IDENTITY_INSERT [Master].[MasterModule] ON
DELETE FROM [Master].[MasterModule] WHERE [Code] = 'CAREER_MANAGEMENT'
DELETE FROM [Master].[MasterModule] WHERE [Code] = 'ADMINISTRATIVE_MANAGEMENT'
DELETE FROM [Master].[MasterModule] WHERE [Code] = 'EMPLOYEES_MANAGEMENT'
SET IDENTITY_INSERT [Master].[MasterModule] OFF


--- Nesrin: add_note_on_turnover_permissions
SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (227, N'NOTE_ON_TURNOVER', 16)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1002, N'LIST_NOTE_ON_TURNOVER', 227)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1003, N'PRINT_NOTE_ON_TURNOVER', 227)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

--- Bechir: add_tier_category_permissions
SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (228, N'TIER_CATEGORY', 3)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1004, N'LIST_TIERCATEGORY', 228)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1005, N'ADD_TIERCATEGORY', 228)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1006, N'UPDATE_TIERCATEGORY', 228)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1007, N'DELETE_TIERCATEGORY', 228)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1008, N'SHOW_TIERCATEGORY', 228)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

--- Amin :fix_show_permission
SET IDENTITY_INSERT [Master].[MasterPermission] ON
UPDATE [Master].[MasterPermission] SET Code = 'SHOW_DOCUMENTREQUEST' where Code = 'READONLY_DOCUMENTREQUEST'
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

SET IDENTITY_INSERT [Master].[MasterRolePermission] ON
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'READONLY_EXITEMPLOYEE' )
SET IDENTITY_INSERT [Master].[MasterRolePermission] OFF

SET IDENTITY_INSERT [Master].[MasterPermission] ON
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'READONLY_EXITEMPLOYEE'
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


--- Ahmed Neji: fix_price_permission

SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (229, N'PRICE_CATEGORY', 19)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1009, N'LIST_PRICECATEGORY', 229)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1010, N'ADD_PRICECATEGORY', 229)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1011, N'UPDATE_PRICECATEGORY', 229)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1012, N'DELETE_PRICECATEGORY', 229)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

