--- add role admin for each company
SET IDENTITY_INSERT [Master].[MasterRole] ON
GO
INSERT [Master].[MasterRole] ([Id], [Code], [Label] ,[IdCompany])
VALUES (1, N'ADMIN', N'Admin',2),(2, N'ADMIN', N'Admin',4),(3, N'ADMIN', N'Admin',5)
GO
SET IDENTITY_INSERT [Master].[MasterRole] OFF


GO
SET IDENTITY_INSERT [Master].[MasterRole] ON
GO
INSERT [Master].[MasterRole] ([Id], [Code], [Label],[IdCompany])
VALUES (6000, N'B2B_ADMIN', N'B2B Admin',2),(6001, N'B2B_ADMIN', N'B2B Admin',4),(6002, N'B2B_ADMIN', N'B2B Admin',5)
GO
SET IDENTITY_INSERT [Master].[MasterRole] OFF
GO