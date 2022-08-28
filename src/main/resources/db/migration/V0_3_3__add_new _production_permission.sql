SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (238, N'MANUFACTURING_GAMME', 13)

INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (239, N'MANUFACTURING_OF', 13)

INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (240, N'MANUFACTURING_NOMENCLATURE', 13)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF


SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1070, N'MANUFACTURING_DISPLAY_GAMME', 238)


INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1071, N'MANUFACTURING_ADD_GAMME', 238)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1072, N'MANUFACTURING_EDIT_GAMME', 238)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1073, N'MANUFACTURING_DELETE_GAMME', 238)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1074, N'MANUFACTURING_GAMME_VISUALISATION', 238)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1075, N'MANUFACTURING_ADD_OF', 239)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1076, N'MANUFACTURING_DISPLAY_OF', 239)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1077, N'MANUFACTURING_EDIT_OF', 239)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1078, N'MANUFACTURING_DELETE_OF', 239)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1079, N'MANUFACTURING_VALIDATE_OF', 239)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1080, N'MANUFACTURING_LAUNCH_OF', 239)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1081, N'MANUFACTURING_ADD_NOMENCLATURE', 240)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1082, N'MANUFACTURING_UPDATE_NOMENCLATURE', 240)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1083, N'MANUFACTURING_DISPLAY_NOMENCLATURE', 240)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1084, N'MANUFACTURING_DELETE_NOMENCLATURE', 240)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

