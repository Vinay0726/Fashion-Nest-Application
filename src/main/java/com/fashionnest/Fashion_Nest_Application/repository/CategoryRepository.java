package com.fashionnest.Fashion_Nest_Application.repository;

import com.fashionnest.Fashion_Nest_Application.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

 Category findByName(String name);

 @Query("Select c from Category c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
 Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);

 List<Category> findByParentCategoryId(Long categoryId);

 @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.name = :name")
 boolean existsByName(@Param("name") String name);

}
