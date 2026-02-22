package com.binayak.ecomm_backend.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer userId;

    @NotBlank(message = "user name cannot be blank")
    @Size(min = 2, message = "must be minimum of 2 characters")
    private String userName;

    @Email(message = "not a valid email")
    @NotBlank(message = "cannot be null")
    private String email;

    @NotBlank(message = "cannot be null")
    @Size(min = 4, message = "minimum size is 4 characters")
    private String password;

}