package com.sd133.question_module.repository;

import com.sd133.question_module.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> getQuestionByCategory(@Param("category") String category);

    @Query(value = "SELECT id FROM question_details ques WHERE ques.category = :category ORDER BY RAND() LIMIT :numOfQs", nativeQuery = true)
    List<String> getRandomQuestionByCategory(@Param("numOfQs") int numQ, @Param("category") String category);

    boolean existsByCategory(String category);

    @Query(value = "SELECT * FROM question_details WHERE id IN (:ids)",nativeQuery = true)
    List<Question> getQuizQuestions(@Param("ids") List<String> ids);
}
