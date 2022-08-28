    SET IDENTITY_INSERT [Master].[MasterCompany] ON
    INSERT INTO [Master].[MasterCompany] ([Id], [Code], [Name], [Email], [IsDeleted], [TransactionUserId],
                                          [Deleted_Token], [DataBaseName], [IdMasterDbSettings],[GarageDataBaseName],[DefaultLanguage])
    VALUES (2, N'C001', N'Spark-TN', N'demo@spark.fr', 0, NULL, NULL, N'MasterGuidTN', 1, N'StarkGarageTN', 'fr')
    INSERT INTO [Master].[MasterCompany] ([Id], [Code], [Name], [Email], [IsDeleted], [TransactionUserId],
                                          [Deleted_Token], [DataBaseName], [IdMasterDbSettings], [GarageDataBaseName],[DefaultLanguage])
    VALUES (4, N'C002', N'Spark-UK', N'demo@spark.fr', 0, NULL, NULL, N'MasterGuidUK', 1, N'StarkGarageUK', 'fr')
    INSERT INTO [Master].[MasterCompany] ([Id], [Code], [Name], [Email], [IsDeleted], [TransactionUserId],
                                          [Deleted_Token], [DataBaseName], [IdMasterDbSettings], [GarageDataBaseName],[DefaultLanguage])
    VALUES (5, N'C003', N'Spark-FR', N'demo@spark.fr', 0, NULL, NULL, N'MasterGuidFR', 1, N'StarkGarageFR', 'fr')
    SET IDENTITY_INSERT [Master].[MasterCompany] OFF