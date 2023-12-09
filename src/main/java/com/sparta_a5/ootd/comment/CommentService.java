package com.sparta_a5.ootd.comment;

import com.amazonaws.services.kms.model.NotFoundException;
import com.sparta_a5.ootd.comment.dto.CommentRequestDTO;
import com.sparta_a5.ootd.comment.dto.CommentResponseDTO;
import com.sparta_a5.ootd.comment.entity.Comment;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.post.repository.PostRepository;
import com.sparta_a5.ootd.post.service.PostService;
import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.repository.UserRepository;
import com.sparta_a5.ootd.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostService postService;
    private final UserService userService;


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

        comment.setContent(commentRequestDTO.getContent());

        return new CommentResponseDTO(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = getUserComment(commentId, user);

        commentRepository.delete(comment);
    }

    private Comment getUserComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다. :" + commentId));

        if(!user.getId().equals(comment.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }

    // 대댓글 생성
    @Transactional
    public Comment createComment(Long postId, CommentRequestDTO commentRequestDTO) {
        User user = userRepository.findById(commentRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("ID를 가진 사용자를 찾을 수 없습니다. : " + commentRequestDTO.getUserId()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("ID를 가진 게시글을 찾을 수 없습니다. : " + postId));

        Comment comment = new Comment(commentRequestDTO);

        Comment parentComment;
        if (commentRequestDTO.getParentId() != null) {
            parentComment = commentRepository.findById(commentRequestDTO.getParentId())
                    .orElseThrow(() -> new NotFoundException("ID를 가진 댓글을 찾을 수 없습니다. : " + commentRequestDTO.getParentId()));
            comment.updateParent(parentComment);
        }

        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }
}
