package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.smartkit.istorybook.models.StoryBookPages;
import tech.smartkit.istorybook.models.StoryPagesViews;

//@see:https://hellokoding.com/restful-api-example-with-spring-boot-spring-data-rest-spring-data-jpa-many-to-many-extra-columns-relationship-and-mysql/
public interface StoryPagesViewsRepository extends JpaRepository<StoryBookPages,Long> {


    @Query("SELECT i FROM StoryPagesViews i WHERE i.storyView =:vid")
    Iterable<StoryPagesViews> findByStoryViewId(@Param("vid") Long vid);

    @Query("SELECT i FROM StoryPagesViews i WHERE i.storyPage =:pid")
    Iterable<StoryPagesViews> findByStoryPageId(@Param("pid") Long pid);
}
