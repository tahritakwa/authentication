INSERT [dbo].[oauth_client_details] ([client_id], [client_secret], [resource_ids], [scope], [authorized_grant_types],
                                     [web_server_redirect_uri], [authorities], [access_token_validity],
                                     [refresh_token_validity], [additional_information], [autoapprove])
VALUES (N'stark-client',
        N'a7c6bf7982f92ed197af475f194d3b750d0b26c0d555f3aad6e00849320062ce4b0628038360725174962662e7bae8e484adf2ad20b20d2d3ca67ee3f7b9f856',
        N'ROLE_RESOURCE,ACCOUNTING,CRM,RH,SALES,MANUFACTURING', N'read,write', N'authorization_code,password,refresh_token,implicit',
        NULL, NULL, 1800, 3600, N'{}', NULL)
GO



GO
INSERT [dbo].[oauth_client_details] ([client_id], [client_secret], [resource_ids], [scope], [authorized_grant_types],
                                     [web_server_redirect_uri], [authorities], [access_token_validity],
                                     [refresh_token_validity], [additional_information], [autoapprove])
VALUES (N'b2b-client',
        N'a7c6bf7982f92ed197af475f194d3b750d0b26c0d555f3aad6e00849320062ce4b0628038360725174962662e7bae8e484adf2ad20b20d2d3ca67ee3f7b9f856',
        N'ROLE_RESOURCE,RH,SALES', N'read,write', N'authorization_code,password,refresh_token,implicit',
        NULL, NULL, 86430, 2592000, N'{}', NULL)
GO