package com.sparta_a5.ootd.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta_a5.ootd.user.entity.QUser;
import com.sparta_a5.ootd.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final EntityManager em;
    private final Long limit = 10L;

    public List<User> findAll(BooleanBuilder builder){
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(QUser.user)
                .from(QUser.user)
                .where(builder)
                .limit(limit)
                .fetch();
    }
}
