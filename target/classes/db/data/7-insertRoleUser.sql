--- Affect role user
GO
INSERT [Master].[MasterRoleUser] ([IdMasterUser], [IdRole] , [IdMasterCompany])
VALUES (5,1,2),(5, 2,4),(5,3,5)
GO


GO
INSERT [Master].[MasterRoleUser] ([IdMasterUser], [IdRole] , [IdMasterCompany])
VALUES (5000, 6000,2),(5000, 6001 , 4), (5000,6002,5 )
GO


INSERT [Master].[MasterRoleUser] ([IdMasterUser], [IdRole], [IdMasterCompany])
VALUES (7, 1,2 )
GO
INSERT [Master].[MasterRoleUser] ([IdMasterUser], [IdRole], [IdMasterCompany])
VALUES (7, 3,5)
GO

