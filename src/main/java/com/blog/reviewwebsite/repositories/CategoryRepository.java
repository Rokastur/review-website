package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT c FROM Category c JOIN FETCH c.followers")
    Set<Category> getCategoriesWithFollowersInitialized();

    String categoryCountQuery = "SELECT COUNT(*) FROM CATEGORY";

    @Query(value = "SELECT c.* FROM category c LEFT JOIN review r ON c.id = r.category_id GROUP BY c.id ORDER BY COUNT(*) DESC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByPostCountDesc(Pageable pageable);

    @Query(value = "SELECT c.* FROM category c LEFT JOIN review r ON c.id = r.category_id GROUP BY c.id ORDER BY COUNT(*) ASC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByPostCountAsc(Pageable pageable);

    @Query(value = "SELECT category_id AS id FROM followed_categories GROUP BY category_id ORDER BY COUNT(*) DESC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByFollowersCountDesc(Pageable pageable);

    @Query(value = "SELECT category_id AS id FROM followed_categories GROUP BY category_id ORDER BY COUNT(*) ASC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByFollowersCountAsc(Pageable pageable);

    @Query(value = "SELECT c.*, date FROM category c LEFT JOIN review r ON c.id = r.id ORDER BY DATE DESC NULLS LAST",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByNewestPost(Pageable pageable);

    @Query(value = "SELECT c.*, date FROM category c LEFT JOIN review r ON c.id = r.id ORDER BY DATE ASC NULLS LAST",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByOldestPost(Pageable pageable);

    @Query(value = "SELECT * FROM category c JOIN followed_categories fc ON c.id = fc.category_id WHERE fc.user_id =:userId",
            countQuery = "SELECT COUNT(*) FROM followed_categories WHERE user_id =:userId",
            nativeQuery = true)
    Page<Category> findAllFollowedByUser(Long userId, Pageable pageable);

}
