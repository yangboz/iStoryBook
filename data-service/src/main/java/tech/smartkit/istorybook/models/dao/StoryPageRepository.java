package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.smartkit.istorybook.models.StoryPage;

import java.util.List;
public interface StoryPageRepository extends JpaRepository<StoryPage,Long> {
    List<StoryPage> findByMode(String mode);
}
