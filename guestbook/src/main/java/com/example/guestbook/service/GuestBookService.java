package com.example.guestbook.service;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {

    // 등록
    Long register(GuestBookDTO dto);

    // 조회
    GuestBookDTO read(Long gno);

    // 전체 조회
    PageResultDTO<GuestBookDTO, GuestBook> list(PageRequestDTO requestDto);

    // 수정
    Long update(GuestBookDTO dto);

    // 삭제
    void delete(Long gno);

    public default GuestBook dtoToEntity(GuestBookDTO dto) {
        return GuestBook.builder()
                .gno(dto.getGno())
                .writer(dto.getWriter())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    public default GuestBookDTO entityToDto(GuestBook entity) {
        return GuestBookDTO.builder()
                .gno(entity.getGno())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .content(entity.getContent())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }
}
