-- youssef add hps operation role 22/10/2021
SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1030, N'ADD_HPS_OPERATION', 39);
SET IDENTITY_INSERT [Master].[MasterPermission] OFF