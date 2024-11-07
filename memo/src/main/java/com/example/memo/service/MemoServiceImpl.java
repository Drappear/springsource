package com.example.memo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    @Override
    public Long create(MemoDTO memoDto) {
        Memo memoEntity = dtoToEntity(memoDto);
        return memoRepository.save(memoEntity).getMno();
    }

    @Override
    public MemoDTO read(Long id) {
        Memo memoEntity = memoRepository.findById(id).get();
        return entityToDto(memoEntity);
    }

    @Override
    public List<MemoDTO> list() {
        List<Memo> entityList = memoRepository.findAll();

        return entityList.stream()
                .map(memo -> entityToDto(memo))
                .collect(Collectors.toList());
    }

    @Override
    public Long update(MemoDTO memoDTO) {
        Memo memoEntity = dtoToEntity(memoDTO);
        return memoRepository.save(memoEntity).getMno();

    }

    @Override
    public void delete(Long id) {
        memoRepository.deleteById(id);
    }

}
