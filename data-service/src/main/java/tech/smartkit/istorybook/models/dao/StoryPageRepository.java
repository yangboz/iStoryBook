package tech.smartkit.istorybook.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.smartkit.istorybook.models.StoryPage;

import java.util.List;
@Repository
public interface StoryPageRepository extends CrudRepository<StoryPage,Long> {
    List<StoryPage> findByMode(String mode);
}
