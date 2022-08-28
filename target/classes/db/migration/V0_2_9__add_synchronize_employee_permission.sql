SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule]) VALUES (1066, N'SYNCHRONIZE_EMPLOYEE', 49)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'SHOW_NOTE' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'SHOW_NOTE'
