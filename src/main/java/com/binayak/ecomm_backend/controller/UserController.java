package com.binayak.ecomm_backend.controller;

import com.binayak.ecomm_backend.config.AppConfig;
import com.binayak.ecomm_backend.response.UserResponse;
import com.binayak.ecomm_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
    }
}
