DELETE FROM [Master].[MasterRolePermission] WHERE [IdPermission] in (select Id from [Master].[MasterPermission] where [Code] = 'REGENERATE_TRANSFER_ORDER' )
DELETE FROM [Master].[MasterPermission] WHERE [Code] = 'REGENERATE_TRANSFER_ORDER'