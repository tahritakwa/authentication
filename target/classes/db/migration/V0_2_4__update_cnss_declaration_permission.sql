DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'ADD_CNSSDECLARATION' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'ADD_CNSSDECLARATION'
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'UPDATE_CNSSDECLARATION' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'UPDATE_CNSSDECLARATION'
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'REGENERATE_CNSS_DECLARATION' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'REGENERATE_CNSS_DECLARATION'

SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1057, N'GENERATE_CNSS_DECLARATION', 76)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF