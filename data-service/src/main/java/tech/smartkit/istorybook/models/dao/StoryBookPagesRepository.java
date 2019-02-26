package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.smartkit.istorybook.models.StoryBookPages;

//@see:https://hellokoding.com/restful-api-example-with-spring-boot-spring-data-rest-spring-data-jpa-many-to-many-extra-columns-relationship-and-mysql/
public interface StoryBookPagesRepository extends JpaRepository<StoryBookPages,Long> {
//    Iterable<StoryBookPages> findByStoryBook(StoryBook storybook);
//    Iterable<StoryBookPages> findByStoryPage(StoryPage storypage);

    @Query("SELECT i FROM StoryBookPages i WHERE i.storyBook =:bid")
    Iterable<StoryBookPages> findByStoryBookId(@Param("bid") Long bid);

    @Query("SELECT i FROM StoryBookPages i WHERE i.storyPage =:pid")
    Iterable<StoryBookPages> findByStoryPageId(@Param("pid")Long pid);


}
