package com.sparta_a5.ootd.comment;

import com.sparta_a5.ootd.comment.dto.CommentRequestDTO;
import com.sparta_a5.ootd.comment.dto.CommentResponseDTO;
import com.sparta_a5.ootd.comment.entity.Comment;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.post.repository.PostRepository;
import com.sparta_a5.ootd.post.service.PostService;
import com.sparta_a5.ootd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final PostRepository postRepository;


    public CommentResponseDTO createComment(CommentRequestDTO dto, User user) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
        );

        Comment comment = new Comment(dto);
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return new CommentResponseDTO(comment);
    }

    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO, User user) {
        Comment comment = getUserComment(commentId, user);

        comment.setComment(commentRequestDTO.getComment());

        return new CommentResponseDTO(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = getUserComment(commentId, user);

        commentRepository.delete(comment);
    }

    private Comment getUserComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

        if(!user.getId().equals(comment.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }
}
