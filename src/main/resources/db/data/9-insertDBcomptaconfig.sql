
GO
SET IDENTITY_INSERT [dbo].[DBComptaConfig] ON 
INSERT [dbo].[DBComptaConfig] ([id], [url], [username], [password], [driverClassName], [companyCode], [env], [module]) VALUES (1, N'jdbc:sqlserver://db:1433;databaseName=Stark-COMPTA', N'sa', N'Spark-It2020', N'com.microsoft.sqlserver.jdbc.SQLServerDriver', N'C001', N'V3', N'accounting')
INSERT [dbo].[DBComptaConfig] ([id], [url], [username], [password], [driverClassName], [companyCode], [env], [module]) VALUES (2, N'jdbc:sqlserver://db:1433;databaseName=Stark-COMPTA-C002', N'sa', N'Spark-It2020', N'com.microsoft.sqlserver.jdbc.SQLServerDriver', N'C002', N'V3', N'accounting')
INSERT [dbo].[DBComptaConfig] ([id], [url], [username], [password], [driverClassName], [companyCode], [env], [module]) VALUES (3, N'jdbc:sqlserver://db:1433;databaseName=Stark-COMPTA-C003', N'sa', N'Spark-It2020', N'com.microsoft.sqlserver.jdbc.SQLServerDriver', N'C003', N'V3', N'accounting')
SET IDENTITY_INSERT [dbo].[DBComptaConfig] OFF

 


