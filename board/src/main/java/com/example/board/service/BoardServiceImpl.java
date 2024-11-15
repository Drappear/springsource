package com.example.board.service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDto) {

        Page<Object[]> result = boardRepository.list(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("bno").descending()));

        Function<Object[], BoardDTO> fn = (en -> entityToDto((Board) en[0], (Member) en[1], (Long) en[2]));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO read(Long bno) {
        Object[] object = boardRepository.getBoardByBno(bno);

        return entityToDto((Board) object[0], (Member) object[1], (Long) object[2]);
    }

    @Override
    public Long update(BoardDTO dto) {
        Board board = boardRepository.findById(dto.getBno()).get();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        return boardRepository.save(board).getBno();
    }

    @Override
    public void remove(Long bno) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

}
