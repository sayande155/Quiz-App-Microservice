package com.sd133.quiz_module.feign;

import com.sd133.quiz_module.model.QuestionWrapper;
import com.sd133.quiz_module.model.QuizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-MODULE")
public interface QuizInterface {
    @GetMapping("question/generateQuiz")
    public ResponseEntity<List<String>> getQuestionsForQuiz(@RequestParam(value = "cat") String category, @RequestParam(value = "numQ") int numOfQs);

    @PostMapping("question/getQuizQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@RequestBody List<String> ids);

    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses);
}
