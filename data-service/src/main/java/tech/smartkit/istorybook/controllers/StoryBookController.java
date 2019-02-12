package tech.smartkit.istorybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import tech.smartkit.istorybook.models.StoryBook;
import tech.smartkit.istorybook.models.StoryBookPage;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.dao.StoryBookPageRepository;
import tech.smartkit.istorybook.models.dao.StoryBookRepository;
import tech.smartkit.istorybook.models.dao.StoryPageRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    StoryBookPageRepository storyBookPageRepository;
    /**
     /cart
     POST / - Create cart
     GET /{id} - Get carts for card with ID = {id}
     POST /{id} - Add CartItem to cart with ID {id}
     DELETE /{id}/{product_id} - Remove product with ID {product_id} from cart with ID {id}
     POST /{id}/quantity - Updates cart item, i.e. set product quantity
     POTS /{id}/order - Create order from cart
     */
    /**
     * View wxshop orders by id.
     */
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
    public ResponseEntity<StoryBookPage> addPage(@PathVariable("bid") long bid, @PathVariable("pid") long pid )  {
        //
        StoryBook storyBook = storyBookRepository.getOne(bid);
        StoryPage storyPage = storyPageRepository.getOne(pid);
        StoryBookPage savedRes = storyBookPageRepository.save(new  StoryBookPage(storyBook,storyPage));
        return new ResponseEntity<StoryBookPage>(savedRes, HttpStatus.OK);
    }

    @RequestMapping("/{id}/pages")
    public ResponseEntity<Iterable<StoryBookPage>> getPages(@PathVariable("id") long id) {
        Iterable<StoryBookPage> findOne = storyBookPageRepository.findByStoryBook(new StoryBook(id));
        //
        if(findOne.equals(null)){
            logger.error("none of book pages found.");
        }
        return new ResponseEntity<Iterable<StoryBookPage>>(findOne, HttpStatus.OK);
    }

}
