package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRepository extends ContentRepository<Review> {

    Page<Review> findAllByHiddenFalseAndCategory(Category category, Pageable pageable);

    Set<Review> findAllByHiddenFalseAndCategory(Category category);

//    v ----------------- order types for reviews by category ----------------- v

    String reviewCountQueryByCategory = "SELECT COUNT(*) FROM Review WHERE hidden = 'false' AND category_id= :categoryId";

    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.id = s.review_id WHERE r.category_id =:categoryId AND hidden = false GROUP BY r.id ORDER BY COUNT(s.review_id) DESC",
            countQuery = reviewCountQueryByCategory,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByTotalScoreDesc(Long categoryId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.id = s.review_id WHERE r.category_id =:categoryId AND hidden = false GROUP BY r.id ORDER BY COUNT(s.review_id) ASC",
            countQuery = reviewCountQueryByCategory,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByTotalScoreAsc(Long categoryId, Pageable pageable);

    @Query(value = "SELECT * FROM Review WHERE hidden = 'false' AND category_id= :categoryId ORDER BY DATE DESC",
            countQuery = reviewCountQueryByCategory,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByDateDesc(Pageable pageable, Long categoryId);

    @Query(value = "SELECT * FROM Review WHERE hidden = 'false' AND category_id= :categoryId ORDER BY DATE ASC",
            countQuery = reviewCountQueryByCategory,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByDateAsc(Pageable pageable, Long categoryId);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.id = c.review_id WHERE r.CATEGORY_id =:categoryId AND hidden = false GROUP BY r.id ORDER BY COUNT(c.review_id) DESC",
            countQuery = reviewCountQueryByCategory,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByCommentCountDesc(Long categoryId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.id = c.review_id WHERE r.CATEGORY_id =:categoryId AND hidden = false GROUP BY r.id ORDER BY COUNT(c.review_id) ASC",
            countQuery = reviewCountQueryByCategory,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByCommentCountAsc(Long categoryId, Pageable pageable);

//    v ----------------- order types for single user reviews ----------------- v

    String reviewCountQueryByUser = "SELECT COUNT(*) FROM Review WHERE hidden = 'false' AND user_id= :userId";

    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.id = s.review_id WHERE user_id =:userId AND hidden = false GROUP BY r.id ORDER BY COUNT(s.review_id) DESC",
            countQuery = reviewCountQueryByUser,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByTotalScoreDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.id = s.review_id WHERE user_id =:userId AND hidden = false GROUP BY r.id ORDER BY COUNT(s.review_id) ASC",
            countQuery = reviewCountQueryByUser, nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByTotalScoreAsc(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE hidden = 'false' AND user_id =:userId ORDER BY DATE DESC",
            countQuery = reviewCountQueryByUser,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByDateDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE hidden = 'false' AND user_id =:userId ORDER BY DATE ASC",
            countQuery = reviewCountQueryByUser,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByDateAsc(Long userId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.id = c.review_id WHERE r.user_id =:userId AND hidden = false GROUP BY r.id ORDER BY COUNT(c.review_id) DESC",
            countQuery = reviewCountQueryByUser,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByCommentCountDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.id = c.review_id WHERE r.user_id =:userId AND hidden = false GROUP BY r.id ORDER BY COUNT(c.review_id) ASC",
            countQuery = reviewCountQueryByUser,
            nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByCommentCountAsc(Long userId, Pageable pageable);
}
