package com.example.mybatis.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "도서명을 입력해주세요")
    private String title;

    @NotBlank(message = "저자명을 입력해주세요")
    private String writer;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    @NotNull(message = "판매가격을 입력해주세요")
    private Integer salePrice;

    @NotBlank(message = "분류를 선택해주세요")
    private String categoryName;
    @NotBlank(message = "출판사를 선택해주세요")
    private String publisherName;

    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;
}
