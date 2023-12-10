package com.sparta_a5.ootd.userPostLike.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta_a5.ootd.userPostLike.entity.QUserPostLike;
import com.sparta_a5.ootd.userPostLike.entity.UserPostLike;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPostLikeQueryRepository {

    private final EntityManager em;
    private final long limit = 10L;

    public List<UserPostLike> findAll(BooleanBuilder builder, Long offset){

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(QUserPostLike.userPostLike)
                .from(QUserPostLike.userPostLike)
                .innerJoin(QUserPostLike.userPostLike.user).fetchJoin()
                .innerJoin(QUserPostLike.userPostLike.post).fetchJoin()
                .where(builder)
                .orderBy()
                .offset(offset)
                .limit(limit)
                .fetch();
    }
}
