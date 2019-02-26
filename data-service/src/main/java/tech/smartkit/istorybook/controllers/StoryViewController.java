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
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.StoryView;
import tech.smartkit.istorybook.models.dao.StoryBookRepository;
import tech.smartkit.istorybook.models.dao.StoryPageRepository;
import tech.smartkit.istorybook.models.dao.StoryViewRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value ="/view")
@Api(value="StoryViewController", description="Operations pertaining to storyviews in iStoryBook")
public class StoryViewController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StoryViewRepository storyViewRepository;
    @Autowired
    StoryPageRepository storyPageRepository;

    @ApiOperation(value = "View a list of available StoryViews", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping("/")
    public Iterable<StoryView> listAll() {
        return storyViewRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Optional<StoryView> getOne(@PathVariable("id") long id) {
        return storyViewRepository.findById(id);
    }



}
