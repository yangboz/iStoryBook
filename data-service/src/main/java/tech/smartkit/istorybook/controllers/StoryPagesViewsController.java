package tech.smartkit.istorybook.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.smartkit.istorybook.models.StoryBookPages;
import tech.smartkit.istorybook.models.StoryPagesViews;
import tech.smartkit.istorybook.models.dao.StoryBookPagesRepository;
import tech.smartkit.istorybook.models.dao.StoryPagesViewsRepository;

@Slf4j
@RestController
@RequestMapping(value ="/pageView")
@Api(value="StoryPagesViewsController", description="Operations pertaining to story book_pages in iStoryBook")
public class StoryPagesViewsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StoryPagesViewsRepository storyPagesViewsRepository;

    @ApiOperation(value = "View a list of available StoryPages", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping("/")
    public Iterable<StoryBookPages> listAll() {
        return storyPagesViewsRepository.findAll();
    }

    @RequestMapping("/v/{id}")
    public Iterable<StoryPagesViews> findByBookId(@PathVariable("id") long id) {
        return storyPagesViewsRepository.findByStoryViewId(id);
    }

    @RequestMapping("/p/{id}")
    public Iterable<StoryPagesViews> findByPageId(@PathVariable("id") long id) {
        return storyPagesViewsRepository.findByStoryPageId(id);
    }

}
