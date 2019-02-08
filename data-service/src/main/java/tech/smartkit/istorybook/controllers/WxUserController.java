package tech.smartkit.istorybook.controllers;

import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.smartkit.istorybook.models.Account;
import tech.smartkit.istorybook.models.StoryBookPage;
import tech.smartkit.istorybook.models.WxUser;
import tech.smartkit.istorybook.models.dao.AccountRepository;
import tech.smartkit.istorybook.models.dao.StoryBookPageRepository;
import tech.smartkit.istorybook.models.dao.WxUserRepository;
import tech.smartkit.istorybook.utils.NumberUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value ="/wx/user")
@Api(value="WxAccountController", description="Operations pertaining to WxUser in iStoryBook")
public class WxUserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WxUserRepository wxUserRepository;
    @Autowired
    AccountRepository accountRepository;
    /**
     /WxUser
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
    @ApiOperation(value = "View a list of available WxUsers", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping("/")
    public Iterable<WxUser> listAll() {
        return wxUserRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Optional<WxUser> getOne(@PathVariable("id") long id) {
        return wxUserRepository.findById(id);
    }
    //by openid
    @RequestMapping("/o/{id}")
    public Optional<WxUser> getOneByOpenid(@PathVariable("id") String id) {
        return wxUserRepository.findByOpenid(id);
    }

    /**
     * Save an accounts info.
     *
     * @return The update save.
     *
     * @see: https://www.leveluplunch.com/java/tutorials/014-post-json-to-spring-rest-webservice/
     */
//    @RequestMapping(value="/save/",method = RequestMethod.POST
//            , consumes = MediaType.APPLICATION_JSON_VALUE
//            , produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value="/save/",method = RequestMethod.POST)
    public ResponseEntity<WxUser> save(
            @RequestBody WxUser wxUser )  {
//			@RequestParam("userInfo") WxUser wxUserInfo) {
        logger.info("accounts-service save() invoked: ");
        //if not existed?
        Optional<WxUser> find = wxUserRepository.findByOpenid(wxUser.getOpenid());
        WxUser saved = null;
        if(find.isPresent()){
            //TODO update.
            logger.info("accounts-service save() with update: " + find);
            return new ResponseEntity<WxUser>(find.get(), HttpStatus.OK);
        }else {
            //save new
            saved = wxUserRepository.save(wxUser);
            logger.info("wxUser-service save() result: " + saved);
            //sync to account
            Account _account = new Account(NumberUtil.nextRandomInt(),saved.getOpenid());
            Account savedAccount = accountRepository.save(_account);
            logger.info("and accounts-service save() result: " + savedAccount);
        }
        return new ResponseEntity<WxUser>(saved, HttpStatus.OK);
    }
}
