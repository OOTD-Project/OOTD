package com.sparta_a5.ootd.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta_a5.ootd.admin.dto.SearchRequestDto;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.post.entity.QPost;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final EntityManager em;
    private final long limit = 10L;

    public List<Post> findAll(BooleanBuilder builder){

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(QPost.post)
                .from(QPost.post)
                .where(builder)
                .limit(limit)
                .fetch();
    }
}
