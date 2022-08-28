SET IDENTITY_INSERT [Master].[MasterSubModule] ON
UPDATE [Master].[MasterSubModule] SET IdModule = (SELECT Id FROM [Master].[MasterModule] WHERE [Code] = 'SALES_SETTINGS' )
WHERE Code = 'TIER_CATEGORY'
SET IDENTITY_INSERT [Master].[MasterSubModule] OFF