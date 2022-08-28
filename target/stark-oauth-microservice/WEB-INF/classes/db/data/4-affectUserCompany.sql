BEGIN TRANSACTION
    ALTER TABLE [Master].[MasterUserCompany]
        DROP CONSTRAINT [FK_MasterUserCompany_MasterCompany]
    ALTER TABLE [Master].[MasterUserCompany]
        DROP CONSTRAINT [FK_MasterUserCompany_MasterUser]

    SET IDENTITY_INSERT [Master].[MasterUserCompany] ON
    INSERT INTO [Master].[MasterUserCompany] ([Id], [IdMasterUser], [IdMasterCompany], [IsDeleted], [TransactionUserId],
                                              [Deleted_Token])
    VALUES (1, 5, 2, 0, NULL, NULL)
    INSERT INTO [Master].[MasterUserCompany] ([Id], [IdMasterUser], [IdMasterCompany], [IsDeleted], [TransactionUserId],
                                              [Deleted_Token])
    VALUES (2, 5, 4, 0, NULL, NULL)
    INSERT INTO [Master].[MasterUserCompany] ([Id], [IdMasterUser], [IdMasterCompany], [IsDeleted], [TransactionUserId],
                                              [Deleted_Token])
    VALUES (3, 5, 5, 0, NULL, NULL)
    SET IDENTITY_INSERT [Master].[MasterUserCompany] OFF

        ALTER TABLE [Master].[MasterUserCompany]
        ADD CONSTRAINT [FK_MasterUserCompany_MasterCompany] FOREIGN KEY ([IdMasterCompany]) REFERENCES [Master].[MasterCompany] ([Id])

    ALTER TABLE [Master].[MasterUserCompany]
        ADD CONSTRAINT [FK_MasterUserCompany_MasterUser] FOREIGN KEY ([IdMasterUser]) REFERENCES [Master].[MasterUser] ([Id])
COMMIT TRANSACTION


    INSERT INTO [Master].[MasterUserCompany] ([IdMasterUser], [IdMasterCompany], [IsDeleted], [TransactionUserId],
                                              [Deleted_Token])
    VALUES (7, 2, 0, NULL, NULL)
    INSERT INTO [Master].[MasterUserCompany] ([IdMasterUser], [IdMasterCompany], [IsDeleted], [TransactionUserId],
                                              [Deleted_Token])
    VALUES (7, 5, 0, NULL, NULL)


    SET IDENTITY_INSERT [Master].[MasterUserCompany] ON
INSERT INTO [Master].[MasterUserCompany] ([Id], [IdMasterUser], [IdMasterCompany], [IsDeleted], [TransactionUserId],
                                          [Deleted_Token], [IsActif])
VALUES (2230, 2006, 2, 0, 0, NULL, 1)
SET IDENTITY_INSERT [Master].[MasterUserCompany] OFF
