package com.example.board.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsertMember() {
        // 30명
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .email("email" + i + "@email.com")
                    .password("password" + i)
                    .name("member" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void testInsertBoard() {
        // 100개
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) (Math.random() * 30) + 1;
            Member member = memberRepository.findById("email" + num + "@email.com").get();
            Board board = Board.builder()
                    .title("board title.." + i)
                    .content("board content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    public void testInsertReply() {
        // 100개
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) (Math.random() * 100) + 1;

            Board bnum = boardRepository.findById((long) num).get();

            Reply reply = Reply.builder()
                    .replyer("replyer" + num)
                    .text("reply text..." + num)
                    .board(bnum)
                    .build();

            replyRepository.save(reply);
        });
    }

}
