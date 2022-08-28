package fr.sparkit.starkoauthmicroservice.convertor;

import fr.sparkit.starkoauthmicroservice.dto.UserDto;
import fr.sparkit.starkoauthmicroservice.model.User;

public final class UserConverter {
    private UserConverter() {
        super();
    }

    public static UserDto modelToDto(User user, Integer lastConnectedCompanyId) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(),
                lastConnectedCompanyId, user.getLastConnectedCompany(), user.getLanguage());
    }
}
