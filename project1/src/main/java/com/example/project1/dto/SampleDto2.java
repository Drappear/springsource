package com.example.project1.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SampleDto2 {
    private Long mno;
    private String firstName;
    private String lastName;
}
