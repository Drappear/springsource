package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public List<Object[]> list() {
        log.info("게시판 + 댓글 + 회원 정보 추출");

        QBoard qBoard = QBoard.board;
        QMember qMember = QMember.member;
        QReply qReply = QReply.reply;

        // select절에 사용할 subquery
        JPQLQuery<Long> replyCnt = JPAExpressions
                .select(qReply.rno.count())
                .from(qReply)
                .where(qReply.board.eq(qBoard))
                .groupBy(qReply.board);

        // 1) from절 부터 구현 : join
        JPQLQuery<Tuple> tuple = from(qBoard)
                .leftJoin(qMember).on(qBoard.writer.eq(qMember))
                .select(qBoard, qMember, replyCnt);
        System.out.println("====== 쿼리문 확인 ======");
        System.out.println(tuple);

        List<Tuple> result = tuple.fetch();

        return result.stream().map(t -> t.toArray()).collect(Collectors.toList());
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {

        QBoard qBoard = QBoard.board;
        QMember qMember = QMember.member;
        QReply qReply = QReply.reply;

        JPQLQuery<Long> replyCnt = JPAExpressions
                .select(qReply.rno.count())
                .from(qReply)
                .where(qReply.board.eq(qBoard))
                .groupBy(qReply.board);

        JPQLQuery<Tuple> tuple = from(qBoard)
                .leftJoin(qMember).on(qBoard.writer.eq(qMember))
                .select(qBoard, qMember, replyCnt);

        // bno > 0 조건
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qBoard.bno.gt(0L));

        if (type != null) {

            // content like '%keyword%' or title like '%keyword%' or writer like '%keyword%'
            BooleanBuilder condidtionBuilder = new BooleanBuilder();

            if (type.contains("c")) {
                // 내용
                condidtionBuilder.or(qBoard.content.contains(keyword));
            }
            if (type.contains("t")) {
                // 제목
                condidtionBuilder.or(qBoard.title.contains(keyword));
            }
            if (type.contains("w")) {
                // 작성자
                condidtionBuilder.or(qMember.name.contains(keyword));
            }
            builder.and(condidtionBuilder);
        }

        tuple.where(builder);

        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            // sort 기준 컬럼명 가져오기
            String prop = order.getProperty();

            // order를 어느 엔티티에 적용할 것인가
            PathBuilder<Board> orderByExpression = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);

    }

    @Override
    public Object[] getBoardByBno(Long bno) {
        QBoard qBoard = QBoard.board;
        QMember qMember = QMember.member;
        QReply qReply = QReply.reply;

        JPQLQuery<Long> replyCnt = JPAExpressions
                .select(qReply.rno.count())
                .from(qReply)
                .where(qReply.board.eq(qBoard))
                .groupBy(qReply.board);

        JPQLQuery<Tuple> tuple = from(qBoard)
                .leftJoin(qMember).on(qBoard.writer.eq(qMember))
                .select(qBoard, qMember, replyCnt).where(qBoard.bno.eq(bno));

        Tuple row = tuple.fetch().get(0);

        return row.toArray();
    }
}
