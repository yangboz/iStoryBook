package tech.smartkit.istorybook.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.smartkit.istorybook.models.StoryBookPage;

import java.util.List;
@Repository
public interface StoryBookPageRepository extends CrudRepository<StoryBookPage,Long> {
    List<StoryBookPage> findByMode(String mode);
}
