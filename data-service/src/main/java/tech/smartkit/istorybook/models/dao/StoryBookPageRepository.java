package tech.smartkit.istorybook.models.dao;

import org.springframework.data.repository.CrudRepository;
import tech.smartkit.istorybook.models.StoryBookPage;

public interface StoryBookPageRepository extends CrudRepository<StoryBookPage,Long> {
}
