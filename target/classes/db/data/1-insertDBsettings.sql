    SET IDENTITY_INSERT [Master].[MasterDbSettings] ON
    INSERT INTO [Master].[MasterDbSettings] ([Id], [Server], [UserId], [UserPassword])
    VALUES (1, N'db', N'sa', N'Spark-It2020')
    SET IDENTITY_INSERT [Master].[MasterDbSettings] OFF