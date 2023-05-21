package cse.java2.project.repository;


import cse.java2.project.model.Question;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Query("SELECT COUNT(q) FROM Question q WHERE q.answerCount=0 ")
  long countQuestionsWithoutAnswers();

  @Query("SELECT COUNT(q) FROM Question q")
  long countQuestions();

  @Query("SELECT AVG(q.answerCount) FROM Question q")
  double avgNumberOfAnswers();

  @Query("SELECT MAX(q.answerCount) FROM Question q")
  int maxNumberOfAnswers();

  @Query("SELECT q.answerCount, COUNT(q) FROM Question q GROUP BY q.answerCount  order by q.answerCount")
  List<Object[]> numberOfAnswersDistribution();

  @Query("SELECT COUNT(q) FROM Question q WHERE q.isAnswered=true ")
  long countQuestionsWithAcceptedAnswer();//What percentage of questions have accepted answers

  @Query("SELECT q.creationDate, COUNT(q) FROM Question q GROUP BY q.answerCount  order by q.answerCount")
  List<Object[]> QuestionSolvedTimeDistribution();

  @Query("SELECT q.creationDate, q.id FROM Question q where q.isAnswered=true ")
  List<Object[]> QuestionListWithAcceptedAnswer();

  @Query("SELECT t, COUNT(t) FROM Question q JOIN q.tags t WHERE t != 'java' GROUP BY t")
  List<Object[]> getTagCount();

  @Query("SELECT qt FROM Question q JOIN q.tags qt WHERE q.id = :questionId")
  List<String> getTagsByQuestionId(@Param("questionId") int questionId);






}
