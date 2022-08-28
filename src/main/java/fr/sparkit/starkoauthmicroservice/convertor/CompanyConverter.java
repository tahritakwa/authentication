package fr.sparkit.starkoauthmicroservice.convertor;

import fr.sparkit.starkoauthmicroservice.dto.CompanyDto;
import fr.sparkit.starkoauthmicroservice.model.Company;

public final class CompanyConverter {
    private CompanyConverter() {
    }

    public static CompanyDto modelToDto(Company company) {
        return new CompanyDto(company.getId(), company.getCode(), company.getName(), company.getEmail(),
                company.getDefaultLanguage());
    }
}
