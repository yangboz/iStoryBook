package tech.smartkit.istorybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
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
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.dao.StoryBookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value ="/book/")
@Api(value="StoryBookController", description="Operations pertaining to storybooks in iStoryBook")
public class StoryBookController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StoryBookRepository storyBookRepository;
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
    @ApiOperation(value = "View a list of available StoryBookPages", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping("/")
    public Iterable<StoryBook> listAll() {
        ObjectMapper mapper = new ObjectMapper();
//// for Hibernate 4.x:
//        mapper.registerModule(new Hibernate4Module());
// or, for Hibernate 5.x
        mapper.registerModule(new Hibernate5Module());
//// or, for Hibernate 3.6
//        mapper.registerModule(new Hibernate3Module());
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
//    @RequestMapping(value="/{id}/add/",method = RequestMethod.POST)
//    public ResponseEntity<StoryBook> addPage(@PathVariable("id") long id, @RequestBody StoryPage storyBookPage )  {
//        Optional<StoryBook> findOne = storyBookRepository.findById(id);
//        StoryBook savedRes = null;
//        //
//        if(findOne.isPresent()){
//            StoryBook  preSaved  = findOne.get();
//            preSaved.getStoryBookPages().add(storyBookPage.getId());
//            savedRes = storyBookRepository.save(preSaved);
//        }else{
//            logger.error("none of book found.");
//        }
//        return new ResponseEntity<StoryBook>(savedRes, HttpStatus.OK);
//    }

//    @RequestMapping("/{id}/page")
//    public ResponseEntity<Set<StoryPage>> getOnePage(@PathVariable("id") long id) {
//        Optional<StoryBook> findOne = storyBookRepository.findById(id);
//        Set<StoryPage> pageRes = new HashSet<>();
//        //
//        if(findOne.isPresent()){
//            pageRes = findOne.get().getPages();
//        }else{
//            logger.error("none of book found.");
//        }
//        return new ResponseEntity<Set<StoryPage>>(pageRes, HttpStatus.OK);
//    }

}
