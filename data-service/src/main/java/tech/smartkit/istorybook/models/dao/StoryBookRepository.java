package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.smartkit.istorybook.models.StoryBookPages;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.StoryBook;

import java.util.List;

@Repository
public interface StoryBookRepository extends JpaRepository<StoryBook,Long> {
    Iterable<StoryBook> findByAuthor(String author);
    List<StoryPage> findByMode(String mode);

//@TODO:SQL optimize
    @Query("SELECT i FROM StoryPage i WHERE id IN (SELECT id FROM StoryBookPages WHERE STORY_BOOK =:bid)")
    Iterable<StoryPage> findStoryBookPagesById(@Param("bid")Long bid);
}
