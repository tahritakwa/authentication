--- add show permissions

SET IDENTITY_INSERT [Master].[MasterPermission] ON 
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule]) VALUES (988, N'SHOW_EXITEMPLOYEE', 51)
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule]) VALUES (989, N'SHOW_RECRUITMENT', 57)
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule]) VALUES (990, N'SHOW_RECRUITMENTREQUEST', 171)
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule]) VALUES (991, N'SHOW_RECRUITMENTOFFER', 172)
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule]) VALUES (992, N'SHOW_EXPENSEREPORT', 64)

DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'READONLY_RECRUITMENT' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'READONLY_RECRUITMENT'
DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'READONLY_EXPENSEREPORT' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'READONLY_EXPENSEREPORT'
SET IDENTITY_INSERT [Master].[MasterPermission] OFF


---add pos permissions
SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (226, N'COUNTER_SALES', 3)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF

SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (993, N'COUNTER_SALES', 226)
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (994, N'GENERATE_INVOICE', 226)

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (995, N'ADD_CASH_MANAGEMENT', 39)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (996, N'UPDATE_CASH_MANAGEMENT', 39)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (997, N'DELETE_CASH_MANAGEMENT', 39)
GO


INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (998, N'OPEN_SESSION_CASH', 39)
GO
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (999, N'CLOSE_SESSION_CASH', 39)
GO

INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1000, N'TICKET_PAYMENT', 226)

SET IDENTITY_INSERT [Master].[MasterPermission] OFF

--- add repair order permissions
SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1001, N'SEND_REPAIR_ORDER_MAIL', 222)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

