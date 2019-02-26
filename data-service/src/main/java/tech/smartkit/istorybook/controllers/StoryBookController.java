package tech.smartkit.istorybook.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.smartkit.istorybook.exceptions.ResourceNotFoundException;
import tech.smartkit.istorybook.models.*;
import tech.smartkit.istorybook.models.dao.*;
import tech.smartkit.istorybook.models.dto.BookPagesResp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value ="/book/")
@Api(value="StoryBookController", description="Operations pertaining to storybooks in iStoryBook")
//@Transactional
public class StoryBookController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StoryBookRepository storyBookRepository;
    @Autowired
    StoryPageRepository storyPageRepository;
    @Autowired
    StoryViewRepository storyViewRepository;
    @Autowired
    StoryBookPagesRepository storyBookPageRepository;
    @Autowired
    StoryPagesViewsRepository storyPagesViewsRepository;

    @ApiOperation(value = "View a list of available StoryBooks", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping("/")
    public Iterable<StoryBook> listAll() {
        return storyBookRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Optional<StoryBook> getOne(@PathVariable("id") long id) {
        return storyBookRepository.findById(id);
    }

    @RequestMapping("/a/{author}")
    public Iterable<StoryBook> findByAuthor(@PathVariable("author") String author) {
        return storyBookRepository.findByAuthor(author);
    }

    @RequestMapping("/m/{mode}")
    public List<StoryPage> findByMode(@PathVariable("mode") String mode) {
        return storyBookRepository.findByMode(mode);
    }

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public ResponseEntity<StoryBook> save( @RequestBody StoryBook storyBook )  {
        StoryBook saved = storyBookRepository.save(storyBook);
        return new ResponseEntity<StoryBook>(saved, HttpStatus.OK);
    }
//@see: https://hellokoding.com/restful-api-example-with-spring-boot-spring-data-rest-spring-data-jpa-many-to-many-extra-columns-relationship-and-mysql/
    @RequestMapping(value="/{bid}/add/{pid}",method = RequestMethod.POST)
    public ResponseEntity<StoryBookPages> addPage(@PathVariable("bid") long bid, @PathVariable("pid") long pid )  {
        //
        StoryBook storyBook = storyBookRepository.getOne(bid);
        if(storyBook.equals(null)){
            logger.error("none of book's pages found.");
            throw new ResourceNotFoundException("bookid-" + bid);
        }
        StoryPage storyPage = storyPageRepository.getOne(pid);
        if(storyPage.equals(null)){
            logger.error("none of book's pages found.");
            throw new ResourceNotFoundException("pageid-" + pid);
        }

        StoryBookPages savedRes = storyBookPageRepository.save(new StoryBookPages(bid,pid));
        return new ResponseEntity<StoryBookPages>(savedRes, HttpStatus.OK);
    }


    @PersistenceContext(type= PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @RequestMapping("/{id}/pages")
//    public ResponseEntity<Iterable<StoryPage>> getPages(@PathVariable("id") long id) {
    public ResponseEntity<List<BookPagesResp>> getPages(@PathVariable("id") long id) {
//        Iterable<StoryBookPages> findOne = storyBookPageRepository.findByStoryBookId(id);
//        //
//        Iterable<StoryPage> findPages = storyBookRepository.findStoryBookPagesById(id);
//        logger.error("find book's pages:"+findPages.toString());
        //
        //SELECT i FROM StoryPage i WHERE id IN \n" +
        //"   (SELECT id FROM StoryBookPages WHERE STORY_BOOK =:bid)
        //
//        SELECT  tsp.*, tspv.*, tsbp.*,tsv.*  from T_STORY_PAGE tsp INNER JOIN
//        T_STORY_BOOK_PAGES tsbp ON tsp.id = tsbp.STORY_PAGE INNER JOIN T_STORY_PAGES_VIEWS tspv ON
//        tspv.STORY_PAGE = tsp.id INNER JOIN T_STORY_VIEW tsv ON tsv.id=tspv.STORY_VIEW  WHERE tsbp.STORY_BOOK=1
        List<BookPagesResp> findPages = entityManager
                .createQuery("SELECT i_tsp,i_tsv FROM StoryPage i_tsp  INNER JOIN\n" +
                                "StoryBookPages tsbp ON i_tsp.id = tsbp.storyPage INNER JOIN StoryPagesViews tspv ON \n" +
                                "tspv.storyPage = i_tsp.id INNER JOIN StoryView i_tsv ON i_tsv.id=tspv.storyView  WHERE tsbp.storyBook=:bid")
                .setParameter( "bid",id)
                .getResultList();

//        assertFalse( postDTOs.isEmpty() );
        logger.info("find book's pages:"+findPages.toString());
        return null;
        //
//        return new ResponseEntity<List<BookPagesResp>>(findPages, HttpStatus.OK);
    }

}
