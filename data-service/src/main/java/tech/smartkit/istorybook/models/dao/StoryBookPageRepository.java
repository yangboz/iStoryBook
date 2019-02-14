package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.smartkit.istorybook.models.StoryBook;
import tech.smartkit.istorybook.models.StoryBookPage;
import tech.smartkit.istorybook.models.StoryPage;

import java.util.spi.LocaleNameProvider;

//@see:https://hellokoding.com/restful-api-example-with-spring-boot-spring-data-rest-spring-data-jpa-many-to-many-extra-columns-relationship-and-mysql/
public interface StoryBookPageRepository extends JpaRepository<StoryBookPage,Long> {
//    Iterable<StoryBookPage> findByStoryBook(StoryBook storybook);
//    Iterable<StoryBookPage> findByStoryPage(StoryPage storypage);

    @Query("SELECT i FROM StoryBookPage i WHERE i.storyBook =:bid")
    Iterable<StoryBookPage> findByStoryBookId(@Param("bid") Long bid);

    @Query("SELECT i FROM StoryBookPage i WHERE i.storyPage =:pid")
    Iterable<StoryBookPage> findByStoryPageId(@Param("pid")Long pid);
}
