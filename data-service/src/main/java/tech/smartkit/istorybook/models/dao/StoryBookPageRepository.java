package tech.smartkit.istorybook.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.smartkit.istorybook.models.StoryBookPage;

import java.util.spi.LocaleNameProvider;

//@see:https://hellokoding.com/restful-api-example-with-spring-boot-spring-data-rest-spring-data-jpa-many-to-many-extra-columns-relationship-and-mysql/
public interface StoryBookPageRepository extends JpaRepository<StoryBookPage,LocaleNameProvider> {
}
