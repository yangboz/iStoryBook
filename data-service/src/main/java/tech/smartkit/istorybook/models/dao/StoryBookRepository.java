package tech.smartkit.istorybook.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.StoryBook;

import java.util.List;

@Repository
public interface StoryBookRepository extends CrudRepository<StoryBook,Long> {
    Iterable<StoryBook> findByAuthor(String author);
    List<StoryPage> findByMode(String mode);
}