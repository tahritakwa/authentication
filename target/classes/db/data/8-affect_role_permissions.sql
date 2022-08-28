DECLARE @id INT, @MaxId INT, @IdChecExistenceOfRole INT

SELECT @id = MIN(Id), @MaxId = MAX(Id) FROM Master.MasterRole where Code ='ADMIN'

WHILE @id is not null and @id <= @MaxId

BEGIN

	Select @IdChecExistenceOfRole = Id from Master.MasterRole where Id = @id

	IF @IdChecExistenceOfRole is not null
	
	BEGIN
			
		Delete from Master.MasterRolePermission where IdRole = @id

		DECLARE @idPermission INT, @MaxIdPermission INT, @IdChecExistenceOfPermission INT

		SELECT @idPermission = MIN(Id), @MaxIdPermission = MAX(Id) FROM Master.MasterPermission

		WHILE @idPermission is not null and @idPermission <= @MaxIdPermission

		BEGIN

			Select @IdChecExistenceOfPermission = Id from Master.MasterPermission where Id = @idPermission

			IF @IdChecExistenceOfPermission is not null
			
			BEGIN
				INSERT INTO [Master].[MasterRolePermission] ([IdRole], [IdPermission], [IsDeleted]) VALUES (@IdChecExistenceOfRole, @IdChecExistenceOfPermission, 0)
			END;
			
			SET @idPermission = @idPermission + 1

		END;

	END;

	Set @id = @id + 1

END;
