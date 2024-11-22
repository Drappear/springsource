package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.entity.constant.MemberRole;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember() {
        // 30명
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .email("member" + i + "@email.com")
                    .password(passwordEncoder.encode("1111"))
                    .name("member" + i)
                    .role(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void testInsertBoard() {
        // 100개
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) (Math.random() * 30) + 1;
            Member member = memberRepository.findById("member" + num + "@email.com").get();
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
            int num2 = (int) (Math.random() * 30) + 1;

            Board bnum = boardRepository.findById((long) num).get();

            Member member = memberRepository.findById("member" + num2 + "@email.com").get();

            Reply reply = Reply.builder()
                    .replyer(member)
                    .text("reply text..." + num)
                    .board(bnum)
                    .build();

            replyRepository.save(reply);
        });
    }

    @Transactional
    @Test
    public void testReadBoard() {
        Board board = boardRepository.findById(100L).get();
        System.out.println(board);

        // 객체 그래프 탐색 : Board(N)-Member(1) 관계
        System.out.println(board.getWriter());

    }

    @Transactional
    @Test
    public void testReadReply() {
        Reply reply = replyRepository.findById(100L).get();
        System.out.println(reply);

        // 객체 그래프 탐색 : Board(N)-Member(1) 관계
        System.out.println(reply.getBoard());

    }

    @Transactional
    @Test
    public void testReadBoardReply() {
        Board board = boardRepository.findById(97L).get();
        System.out.println(board);
        System.out.println(board.getReplies());
    }

    @Test
    public void testJoin() {
        List<Object[]> result = boardRepository.list();

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
            // Board board = (Board) objects[0]
            // Member member = (Member) objects[1]
            // Long replyCnt = (Long) objects[2]
        }
    }

    @Test
    public void testJoinList() {
        // Pageable pageable = PageRequest.of(0, 10,
        // Sort.by("bno").descending().and(Sort.by("title").descending()));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.list("tc", "content", pageable);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void testJoinRow() {
        Object[] object = boardRepository.getBoardByBno(97L);
        System.out.println(Arrays.toString(object));
    }

    @Commit
    @Transactional
    @Test
    public void testReplyRemove() {
        replyRepository.deleteByBno(1L);
        boardRepository.deleteById(1L);
    }

    @Test
    public void testReplyRemove2() {
        // 부모 제거시 자식(reply)제거
        boardRepository.deleteById(95L);
    }

    @Test
    public void testReplyList() {
        Board board = Board.builder().bno(13L).build();
        List<Reply> list = replyRepository.findByBoardOrderByRno(board);

        list.forEach(b -> System.out.println(b));
    }

    @Test
    public void testReplyUpdate() {
        // 댓글 수정
        Reply reply = replyRepository.findById(111L).get();
        System.out.println(reply);

        // 내용 수정
        reply.setText("내용 수정");
        System.out.println(replyRepository.save(reply));
    }

}
