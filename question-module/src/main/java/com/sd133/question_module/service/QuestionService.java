package com.sd133.question_module.service;


import com.sd133.question_module.model.Question;
import com.sd133.question_module.model.QuestionWrapper;
import com.sd133.question_module.model.QuizResponse;
import com.sd133.question_module.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<?> getAllQuestions() {
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            if (!questionRepository.existsById(question.getQuestionId())) {
                questionRepository.save(question);
                return new ResponseEntity<>("Question Added Successfully", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Add Question Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getQuestionsByCategory(String category) {
        List<Question> questions = questionRepository.getQuestionByCategory(category);
        if (questions.isEmpty()) {
            return new ResponseEntity<>("No questions available", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        try {
            if (questionRepository.existsById(question.getQuestionId())) {
                questionRepository.save(question);
                return new ResponseEntity<>("Question Updated Successfully", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Update Question Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestionById(String id) {
        try {
            Question question = questionRepository.getReferenceById(id);
            questionRepository.delete(question);
            return new ResponseEntity<>("Question Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Question Not Exists", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<String>> getQuestionsForQuiz(String category, int numOfQs) {
        if (!questionRepository.existsByCategory(category))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<String> quesIds = questionRepository.getRandomQuestionByCategory(numOfQs, category);
        return new ResponseEntity<>(quesIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(List<String> ids) {
        List<Question> ques = new ArrayList<>();
        try {
            ques = questionRepository.getQuizQuestions(ids);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<QuestionWrapper> questionsForQuiz = new ArrayList<>();
        for (Question q : ques) {
            questionsForQuiz.add(new QuestionWrapper(q.getQuestionId(), q.getQuestionBody(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()));
        }
        return new ResponseEntity<>(questionsForQuiz, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<QuizResponse> responses) {
        int correct = 0;
        for(QuizResponse response : responses){
            if(response.getResponse().equals(questionRepository.findById(response.getId()).get().getCorrectOption())){
                correct ++;
            }
        }
        return new ResponseEntity<>(correct, HttpStatus.OK);
    }
}
