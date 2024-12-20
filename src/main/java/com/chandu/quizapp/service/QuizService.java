package com.chandu.quizapp.service;

import com.chandu.quizapp.model.Question;
import com.chandu.quizapp.model.QuestionWrapper;
import com.chandu.quizapp.model.Quiz;
import com.chandu.quizapp.model.Response;
import com.chandu.quizapp.repository.QuestionRepository;
import com.chandu.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService
{
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<?> createQuiz(String category,int numQ,String title)
    {
        List<Question> questions=questionRepository.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        return new ResponseEntity<>(quizRepository.save(quiz),HttpStatus.CREATED);
    }

    public ResponseEntity<?> getQuiz(int id)
    {
        Quiz quiz=quizRepository.findById(id).orElseThrow(()->new RuntimeException("Quiz not Found"));

        List<Question> questions=quiz.getQuestions();

        List<QuestionWrapper> questionWrappers=questions.stream().map(q->new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4())).toList();

        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    public ResponseEntity<?> calculateResult(int id, List<Response> responses)
    {
        Quiz quiz=quizRepository.findById(id).orElseThrow(()->new RuntimeException("Quiz not found"));

        List<Question> questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response response : responses)
        {
            if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
