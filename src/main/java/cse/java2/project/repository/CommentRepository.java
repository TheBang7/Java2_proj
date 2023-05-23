package cse.java2.project.repository;

import cse.java2.project.model.Answer;
import cse.java2.project.model.Comment;
import cse.java2.project.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  @Query("SELECT c FROM Comment c WHERE (:postId IS NULL OR c.postId=:postId) AND (:id IS NULL OR c.commentId = :id)")
  List<Comment> getFilteredComments(@Param("postId") Integer postId,
      @Param("id") Integer id);

}
