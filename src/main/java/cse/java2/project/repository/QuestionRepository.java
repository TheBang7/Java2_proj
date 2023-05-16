package cse.java2.project.repository;


import cse.java2.project.model.Question;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Query("SELECT COUNT(q) FROM Question q WHERE q.answers IS EMPTY")
  long countQuestionsWithoutAnswers();

  @Query("SELECT AVG(q.answerCount) FROM Question q")
  double avgNumberOfAnswers();

  @Query("SELECT MAX(q.answerCount) FROM Question q")
  int maxNumberOfAnswers();

  @Query("SELECT q.answerCount, COUNT(q) FROM Question q GROUP BY q.answers.size")
  List<Object[]> numberOfAnswersDistribution();
}
