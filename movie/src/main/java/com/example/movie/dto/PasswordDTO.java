package com.example.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordDTO {
    private String email;
    private String currentPassword;
    private String newPassword;
}
