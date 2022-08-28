SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (231, N'CRM_CAMPAIGN', 14)
GO
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1023, N'CRM_PH_CAMPAIGN', 231)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1024, N'VIEW_PH_CAMPAIGN', 231)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1025, N'ADD_PH_CAMPAIGN', 231)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1026, N'DELETE_PH_CAMPAIGN', 231)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1027, N'EDIT_PH_CAMPAIGN', 231)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1028, N'OWN_PH_CAMPAIGN', 231)
GO
SET IDENTITY_INSERT [Master].[MasterPermission] OFF