package com.sd133.question_module.controller;


import com.sd133.question_module.model.Question;
import com.sd133.question_module.model.QuestionWrapper;
import com.sd133.question_module.model.QuizResponse;
import com.sd133.question_module.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private Environment environment;

    @Autowired
    private QuestionService questionService;

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getQuestionsByCategory(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return questionService.getQuestionsByCategory(category);
        } else {
            return questionService.getAllQuestions();
        }
    }

    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {

        return questionService.updateQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable String id) {
        return questionService.deleteQuestionById(id);
    }

    @GetMapping("generateQuiz")
    public ResponseEntity<List<String>> getQuestionsForQuiz(@RequestParam(value="cat") String category, @RequestParam(value = "numQ") int numOfQs){
        return questionService.getQuestionsForQuiz(category, numOfQs);
    }

    @PostMapping("getQuizQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@RequestBody List<String> ids){
//        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuizQuestions(ids);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses){
        return questionService.getScore(responses);
    }

}
