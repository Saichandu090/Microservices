package com.chandu.quizapp.controller;

import com.chandu.quizapp.model.Response;
import com.chandu.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController
{
    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title)
    {
        return quizService.createQuiz(category,numQ,title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable int id)
    {
        return quizService.getQuiz(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses)
    {
        return quizService.calculateResult(id,responses);
    }
}
