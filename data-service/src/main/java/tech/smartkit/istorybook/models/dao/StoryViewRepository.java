package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.smartkit.istorybook.models.StoryView;

@Repository
public interface StoryViewRepository extends JpaRepository<StoryView,Long> {

}
