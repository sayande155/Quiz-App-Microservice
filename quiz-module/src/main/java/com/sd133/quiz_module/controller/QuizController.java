package com.sd133.quiz_module.controller;


import com.sd133.quiz_module.model.Quiz;
import com.sd133.quiz_module.model.QuizDto;
import com.sd133.quiz_module.model.QuizResponse;
import com.sd133.quiz_module.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQ(), quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable int id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> getScore(@PathVariable int id, @RequestBody List<QuizResponse> responses) {
        return quizService.getScore(id, responses);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id) {
        return quizService.deleteQuiz(id);
    }

}
