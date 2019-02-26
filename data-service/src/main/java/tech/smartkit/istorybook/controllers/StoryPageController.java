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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.smartkit.istorybook.models.StoryBook;
import tech.smartkit.istorybook.models.StoryBookPages;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.dao.StoryBookPagesRepository;
import tech.smartkit.istorybook.models.dao.StoryBookRepository;
import tech.smartkit.istorybook.models.dao.StoryPageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value ="/page")
@Api(value="StoryPageController", description="Operations pertaining to storypages in iStoryBook")
public class StoryPageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StoryBookRepository storyBookRepository;
    @Autowired
    StoryPageRepository storyPageRepository;
    @Autowired
    StoryBookPagesRepository storyBookPageRepository;
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
    @ApiOperation(value = "View a list of available StoryPages", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping("/")
    public Iterable<StoryPage> listAll() {
        return storyPageRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Optional<StoryPage> getOne(@PathVariable("id") long id) {
        return storyPageRepository.findById(id);
    }

    @RequestMapping("/m/{mode}")
    public List<StoryPage> findByMode(@PathVariable("mode") String mode) {
        return storyPageRepository.findByMode(mode);
    }

    @RequestMapping("/{id}/books")
    public ResponseEntity<List<StoryBook>> getBooks(@PathVariable("id") long id) {
        Iterable<StoryBookPages> findOne = storyBookPageRepository.findByStoryPageId(id);
        //
        List<StoryBook> findBooks = new ArrayList<StoryBook>();
        if (findOne.equals(null)) {
            logger.error("none of book's pages found.");
        } else {
            //FIXME,SQL optimize.
            for (StoryBookPages bookPage : findOne) {
                long bookId = bookPage.getStoryBook();
                Optional<StoryBook> storyBook = storyBookRepository.findById(bookId);
                if (storyBook.isPresent()) {
                    findBooks.add(storyBook.get());
                }
            }
            logger.error("find page's books:" + findBooks.toString());
        }
        return new ResponseEntity<List<StoryBook>>(findBooks, HttpStatus.OK);
    }

}
