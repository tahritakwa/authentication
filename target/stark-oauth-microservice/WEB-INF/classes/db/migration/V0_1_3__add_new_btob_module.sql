
---Ghazoua : add BTOB module

SET IDENTITY_INSERT [Master].[MasterModule] ON
INSERT INTO [Master].[MasterModule] ([Id], [Code]) VALUES (39, N'SALES_BTOB')
SET IDENTITY_INSERT [Master].[MasterModule] OFF

--- Ghazoua: add btobOrders to mastersubmodule
SET IDENTITY_INSERT [Master].[MasterSubModule] ON
INSERT INTO [Master].[MasterSubModule] ([Id], [Code], [IdModule])
VALUES (230, N'BTOB_ORDERS', 39)
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF

---Ghazoua: add btob orders permission to MasterPermisssion
SET IDENTITY_INSERT [Master].[MasterPermission] ON
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1014, N'SYNCHRONIZATION_ORDERS_BTOB', 230)
INSERT [Master].[MasterPermission] ([Id], [Code], [IdSubModule])
VALUES (1015, N'LIST_ORDERS_BTOB',230)
SET IDENTITY_INSERT [Master].[MasterPermission] OFF

