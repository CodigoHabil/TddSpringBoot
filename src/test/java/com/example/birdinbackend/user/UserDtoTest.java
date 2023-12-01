package com.example.birdinbackend.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {
    UserMapper mapper = UserMapper.INSTANCE;
    @Test
    void itShouldCreateAUserDtoFromUserClass() {
        User user = new User("sarah1", "abc123", "eee");
        UserDto userDto = mapper.userDto(user);

        System.out.println(userDto.toString());
        //Assertions.assertThat(userDto.getUsername()).isEqualTo("sarah1");
    }

}