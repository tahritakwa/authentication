-- Ahmed add deposit invoice permission    13/12/2021

SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (236, N'DEPOSIT_INVOICE', 3)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF

SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1068, N'GENERATE_DEPOSIT_INVOICE', 236);
SET IDENTITY_INSERT [Master].[MasterPermission] OFF
