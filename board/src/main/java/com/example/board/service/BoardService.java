package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDto);

    BoardDTO read(Long bno);

    Long update(BoardDTO dto);

    void remove(Long bno);

    public default BoardDTO entityToDto(Board board, Member member, Long replyCnt) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .regDate(board.getRegDate())
                .updateDate(board.getUpdateDate())
                .replyCnt(replyCnt)
                .build();
    }

    public default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }

}
