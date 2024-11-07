package com.example.memo.service;

import java.util.List;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;

public interface MemoService {
    // CRUD method
    Long create(MemoDTO memoDto);

    MemoDTO read(Long id);

    List<MemoDTO> list();

    Long update(MemoDTO memoDTO);

    void delete(Long id);

    // dto ==> entity
    public default Memo dtoToEntity(MemoDTO memoDto) {
        return Memo.builder()
                .mno(memoDto.getMno())
                .memoText(memoDto.getMemoText())
                .build();
    }

    // entity ==> dto
    public default MemoDTO entityToDto(Memo memoEntity) {
        return MemoDTO.builder()
                .mno(memoEntity.getMno())
                .memoText(memoEntity.getMemoText())
                .createdDateTime(memoEntity.getCreatedDateTime())
                .lastModifiedDateTime(memoEntity.getLastModifiedDateTime())
                .build();
    }
}
