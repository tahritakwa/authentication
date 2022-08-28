--houssem 28-08-2020 add Nomber of licence
INSERT [Master].[CompanyLicence] ([IdMasterCompany], [NombreERPUser], [NombreB2BUser], [ExpirationDate],
                                  [IntialDate], [IsDeleted], [Deleted_Token])

select  Id , 50 ,10 ,NULL, CAST(N'2020-08-28' AS Date) , 0, NULL from Master.MasterCompany

