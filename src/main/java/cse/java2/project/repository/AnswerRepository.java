package cse.java2.project.repository;

import cse.java2.project.model.Answer;
import cse.java2.project.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {


  @Query("SELECT a.creationDate FROM Answer a WHERE a.isAccepted = true AND a.questionId = :id")
  Long getCreationDateOfAcceptedAnswer(@Param("id") Integer id);

  @Query("SELECT COUNT(DISTINCT a.questionId) FROM Answer a WHERE a.isAccepted = false AND a.upVoteCount > (SELECT MAX(a2.upVoteCount) FROM Answer a2 WHERE a2.questionId = a.questionId AND a2.isAccepted = true)")
  int countQuestionsWithMoreUpvote();


  @Query("SELECT COUNT(a) FROM Answer a")
  long countAnswers();
}