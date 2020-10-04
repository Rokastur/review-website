package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.CommentRepository;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private ReviewRepository reviewRepository;

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
    }

    public Page<Comment> getAllCommentsByReview(int pageNumber, Long reviewId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return commentRepository.findAllByReviewId(reviewId, pageable);
    }

    public Comment saveOrUpdateComment(Comment comment, User user, Long id) {
        Review review = reviewRepository.getOne(id);
        comment.setReview(review);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Page<Comment> getAllCommentsByDateDesc(int pageNumber, Long reviewId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return commentRepository.findAllByReviewIdAndOrderByDateDesc(reviewId, pageable);
    }

    public Page<Comment> getAllCommentsByDateAsc(int pageNumber, Long reviewId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return commentRepository.findAllByReviewIdAndOrderByDateAsc(reviewId, pageable);
    }

    public Page<Comment> getAllCommentsByScoreDesc(int pageNumber, Long reviewId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return commentRepository.findAllByReviewIdAndOrderByTotalScoreDesc(reviewId, pageable);
    }

    public Page<Comment> getAllCommentsByScoreAsc(int pageNumber, Long reviewId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return commentRepository.findAllByReviewIdAndOrderByTotalScoreAsc(reviewId, pageable);
    }
}