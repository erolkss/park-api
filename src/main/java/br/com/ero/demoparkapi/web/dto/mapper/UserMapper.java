package br.com.ero.demoparkapi.web.dto.mapper;

import br.com.ero.demoparkapi.config.entity.User;
import br.com.ero.demoparkapi.web.dto.UserCreateDto;
import br.com.ero.demoparkapi.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.context.properties.PropertyMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserCreateDto userCreateDto) {
        return new ModelMapper().map(userCreateDto, User.class);
    }

    public static UserResponseDto toDto(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDto> propertyMap = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(propertyMap);
        return mapper.map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<User> users) {
        return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}