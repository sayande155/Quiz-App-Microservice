package com.sd133.quiz_module.service;


import com.sd133.quiz_module.feign.QuizInterface;
import com.sd133.quiz_module.model.QuestionWrapper;
import com.sd133.quiz_module.model.Quiz;
import com.sd133.quiz_module.model.QuizResponse;
import com.sd133.quiz_module.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numOfQs, String title) {
        List<String> questionIdsForQuiz = quizInterface.getQuestionsForQuiz(category, numOfQs).getBody();

        if (questionIdsForQuiz == null) {
            return new ResponseEntity<>("No questions available", HttpStatus.BAD_REQUEST);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionId(questionIdsForQuiz);

        try {
            quizRepository.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getQuiz(int id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>("Quiz doesn't exist", HttpStatus.NOT_FOUND);
        }

        Quiz quiz = quizOptional.get();

        List<String> questionsIdsList = quiz.getQuestionId();
        List<QuestionWrapper> questionsForClient = quizInterface.getQuizQuestions(questionsIdsList).getBody();

        if(questionsForClient.isEmpty()){
            new ResponseEntity<>("No question available", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionsForClient, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(int id, List<QuizResponse> responses) {
        int correct = 0;
        try {
            correct = quizInterface.getScore(responses).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(correct, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteQuiz(int id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return new ResponseEntity<>("Quiz deletion successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
    }
}
