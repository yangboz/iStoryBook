package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.StoryBook;

import java.util.List;

public interface StoryBookRepository extends JpaRepository<StoryBook,Long> {
    Iterable<StoryBook> findByAuthor(String author);
    List<StoryPage> findByMode(String mode);
}
