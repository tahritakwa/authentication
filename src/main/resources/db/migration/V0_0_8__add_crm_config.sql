GO
CREATE TABLE [dbo].[DBCrmConfig](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [url] [nvarchar](255) NOT NULL,
    [username] [nvarchar](255) NOT NULL,
    [password] [nvarchar](255) NOT NULL,
    [driverClassName] [nvarchar](255) NOT NULL,
    [companyCode] [nvarchar](255) NOT NULL,
    [env] [nvarchar](255) NULL,
    [module] [nvarchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
    ) ON [PRIMARY]
    GO