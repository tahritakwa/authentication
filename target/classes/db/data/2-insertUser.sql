    SET IDENTITY_INSERT [Master].[MasterUser] ON
    INSERT INTO [Master].[MasterUser] ([Id], [FirstName], [LastName], [Email], [Login], [Password], [Token],
                                       [LastConnectedCompany], [IsDeleted], [TransactionUserId], [Deleted_Token],[IsBtoB], [Language])
    VALUES (5, N'admin', N'admin', N'demo@spark-it.fr', N'Administrator',
            N'9fd3ce560c85f097b2be8784844ad05da15132cfe1f1696e3e220f4a79cde33c9972dbe2aa750775b053ed12c723969f13e880ac66199a02e4d368b38cbdaeb8',
            NULL, N'C001', 0, NULL, NULL, 0, 'fr')
    SET IDENTITY_INSERT [Master].[MasterUser] OFF


    SET IDENTITY_INSERT [Master].[MasterUser] ON
    INSERT INTO [Master].[MasterUser] ([Id], [FirstName], [LastName], [Email], [Login], [Password], [Token],
                                       [LastConnectedCompany], [IsDeleted], [TransactionUserId], [Deleted_Token] ,[IsBtoB], [Language])
    VALUES (2006, N'Houssem', N'Ben Mustapha', N'houssem.benmustapha@spark-it.tn', NULL,
            N'9fd3ce560c85f097b2be8784844ad05da15132cfe1f1696e3e220f4a79cde33c9972dbe2aa750775b053ed12c723969f13e880ac66199a02e4d368b38cbdaeb8',
            NULL, N'C003', 0, 0, NULL, 0, 'fr')
    SET IDENTITY_INSERT [Master].[MasterUser] OFF    

    
/****** Object:  Table [Master].[MasterUser] : Add b2b user   Script Date: 22/07/2021 16:43:20 ******/

SET IDENTITY_INSERT [Master].[MasterUser] ON
    INSERT INTO [Master].[MasterUser] ([Id], [FirstName], [LastName], [Email], [Login], [Password], [Token],
                                       [LastConnectedCompany], [IsDeleted], [TransactionUserId], [Deleted_Token],[IsBtoB], [Language])
    VALUES (5000, N'admin', N'admin', N'b2buser@spark-it.fr', N'Administrator',
            N'9fd3ce560c85f097b2be8784844ad05da15132cfe1f1696e3e220f4a79cde33c9972dbe2aa750775b053ed12c723969f13e880ac66199a02e4d368b38cbdaeb8',
            NULL, N'C001', 0, NULL, NULL, 1, 'fr')
    SET IDENTITY_INSERT [Master].[MasterUser] OFF


--- User for manufactoring module
SET IDENTITY_INSERT [Master].[MasterUser] ON
INSERT [Master].[MasterUser] ([Id], [FirstName], [LastName], [Email], [Login], [Password], [Token],
                                       [LastConnectedCompany], [IsDeleted], [TransactionUserId], [Deleted_Token],[IsBtoB], [Language])
    VALUES (7, N'production', N'test', N'prod@spark-it.fr', N'Administrator',
            N'a4a2321bcf3a3894886981bcd473f7e7fde4e9fe7a144a295c100322ba4c5611e880dfcd6f4c634fa459272af90c0c8ad06970d1ee20ba30408afd225a5ebdad',
            NULL, N'C001', 0, NULL, NULL, 0, 'fr')
SET IDENTITY_INSERT [Master].[MasterUser] OFF


