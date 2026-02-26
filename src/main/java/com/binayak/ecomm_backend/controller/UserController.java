package com.binayak.ecomm_backend.controller;

import com.binayak.ecomm_backend.config.AppConfig;
import com.binayak.ecomm_backend.payload.UserDto;
import com.binayak.ecomm_backend.response.ApiResponse;
import com.binayak.ecomm_backend.response.UserResponse;
import com.binayak.ecomm_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserResponse> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = AppConfig.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConfig.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConfig.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConfig.SORT_DIR, required = false) String sortDir
    ) {
        UserResponse userResponse = this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId) {
        UserDto user = this.userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto newUser = this.userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted", true), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<UserResponse> searchUser(
            @PathVariable("keyword") String keyword,
            @RequestParam(value = "pageNumber", defaultValue = AppConfig.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConfig.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConfig.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConfig.SORT_DIR, required = false) String sortDir
    ) {
        UserResponse getUser = this.userService.searchUser(keyword, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getUser, HttpStatus.FOUND);
    }
}