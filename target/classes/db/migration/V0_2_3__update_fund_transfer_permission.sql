-- youssef add validate fund trnasfer role 26/10/2021


SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (232, N'INTERNAL_FUND_TRANSFER', 4)
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (233, N'EXTERNAL_FUND_TRANSFER', 4)
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (234, N'BANK_FUND_TRANSFER', 4)
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (235, N'CASH_FUND_TRANSFER', 4)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF

SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1033, N'LIST_INTERNAL_FUND_TRANSFER', 232);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1034, N'ADD_INTERNAL_FUND_TRANSFER', 232);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1035, N'UPDATE_INTERNAL_FUND_TRANSFER', 232);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1036, N'DELETE_INTERNAL_FUND_TRANSFER', 232);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1037, N'SHOW_INTERNAL_FUND_TRANSFER', 232);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1038, N'VALIDATE_INTERNAL_FUND_TRANSFER', 232);

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1039, N'LIST_EXTERNAL_FUND_TRANSFER', 233);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1040, N'ADD_EXTERNAL_FUND_TRANSFER', 233);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1041, N'UPDATE_EXTERNAL_FUND_TRANSFER', 233);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1042, N'DELETE_EXTERNAL_FUND_TRANSFER', 233);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1043, N'SHOW_EXTERNAL_FUND_TRANSFER', 233);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1044, N'VALIDATE_EXTERNAL_FUND_TRANSFER', 233);

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1045, N'LIST_BANK_FUND_TRANSFER', 234);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1046, N'ADD_BANK_FUND_TRANSFER', 234);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1047, N'UPDATE_BANK_FUND_TRANSFER', 234);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1048, N'DELETE_BANK_FUND_TRANSFER', 234);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1049, N'SHOW_BANK_FUND_TRANSFER', 234);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1050, N'VALIDATE_BANK_FUND_TRANSFER', 234);

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1051, N'LIST_CASH_FUND_TRANSFER', 235);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1052, N'ADD_CASH_FUND_TRANSFER', 235);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1053, N'UPDATE_CASH_FUND_TRANSFER', 235);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1054, N'DELETE_CASH_FUND_TRANSFER', 235);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1055, N'SHOW_CASH_FUND_TRANSFER', 235);
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1056, N'VALIDATE_CASH_FUND_TRANSFER', 235);

DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'LIST_FUNDS_TRANSFER' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'LIST_FUNDS_TRANSFER'
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'ADD_FUNDS_TRANSFER' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'ADD_FUNDS_TRANSFER'
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'UPDATE_FUNDS_TRANSFER' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'UPDATE_FUNDS_TRANSFER'
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'DELETE_FUNDS_TRANSFER' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'DELETE_FUNDS_TRANSFER'
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'SHOW_FUNDS_TRANSFER' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'SHOW_FUNDS_TRANSFER'
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

SET IDENTITY_INSERT [Master].[MasterSubModule] ON
DELETE FROM [Master].[MasterSubModule] WHERE [Code] = 'CASH_REGISTERS_FUNDS_TRANSFER'
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF