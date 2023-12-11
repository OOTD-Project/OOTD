package com.sparta_a5.ootd.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta_a5.ootd.user.entity.Follow;
import com.sparta_a5.ootd.user.entity.QFollow;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepository {

    private final EntityManager em;

    private final long limit = 10L;

    public List<Follow> findAll(BooleanBuilder builder, Sort.Direction direction, Long offset) {
        OrderSpecifier<LocalDateTime> order = new OrderSpecifier<>(
                direction == Sort.Direction.ASC ? Order.ASC : Order.DESC, QFollow.follow.createdAt
        );

        JPAQueryFactory query = new JPAQueryFactory(em);

         return query
                .select(QFollow.follow)
                .from(QFollow.follow)
                .innerJoin(QFollow.follow.following).fetchJoin()
                .innerJoin(QFollow.follow.follower).fetchJoin()
                .where(builder)
                .orderBy(order)
                .offset((offset-1) * limit)
                .limit(limit)
                .fetch();
    }
}
