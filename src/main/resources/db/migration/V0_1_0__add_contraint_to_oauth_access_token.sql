ALTER TABLE [dbo].[oauth_access_token]
ALTER COLUMN [user_name] [varchar] (450);

ALTER TABLE [dbo].[oauth_access_token] ADD UNIQUE (user_name);
