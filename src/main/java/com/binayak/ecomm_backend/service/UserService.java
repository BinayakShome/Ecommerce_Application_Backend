package com.binayak.ecomm_backend.service;

import com.binayak.ecomm_backend.payload.UserDto;
import com.binayak.ecomm_backend.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    void deleteUser(Integer userId);

    UserDto getUserById(Integer userId);

    UserResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    UserResponse searchUser(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
