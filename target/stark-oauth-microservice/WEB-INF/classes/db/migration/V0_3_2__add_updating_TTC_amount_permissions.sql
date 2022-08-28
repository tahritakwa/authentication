SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (237, N'UPDATE_TTC_AMOUNT', 3)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF

SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1069, N'UPDATE_TTC_AMOUNT', 237);
SET IDENTITY_INSERT [Master].[MasterPermission] OFF