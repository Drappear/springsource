package com.example.board.service;

import java.util.List;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;

public interface ReplyService {
    Long register(ReplyDTO replyDto);

    List<ReplyDTO> list(Long bno);

    List<ReplyDTO> read(Long rno);

    Long modify(ReplyDTO replyDto);

    void remove(Long rno);

    // entitiy to dto
    public default ReplyDTO entityToDto(Reply entity) {
        return ReplyDTO.builder()
                .rno(entity.getRno())
                .text(entity.getText())
                .replyer(entity.getReplyer())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    // dto to entity
    public default Reply dtoToEntity(ReplyDTO dto) {
        Board board = Board.builder().bno(dto.getBno()).build();
        return Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
    }

}
