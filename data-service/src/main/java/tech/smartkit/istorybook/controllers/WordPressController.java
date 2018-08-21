package tech.smartkit.istorybook.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value ="/wp/post")
@Api(value="WordPressController", description="Operations pertaining to wordpress in iStoryBook")
public class WordPressController {
    protected Logger logger = Logger.getLogger(WordPressController.class
            .getName());

     /**
     * View wp posts by nickName.
     */
     //https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
     @ApiOperation(value = "View wp posts by nickName.", response = Iterable.class)
     @ApiResponses(value = {
             @ApiResponse(code = 200, message = "Successfully retrieved list"),
             @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
             @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
             @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
     })
    @RequestMapping(value = "/byNickName/{nickName}", method= RequestMethod.GET, produces = "application/json")
    public void getMyPosts(@PathVariable("nickName") String nickName) {
        logger.info("List of wp posts by nickName:" + nickName);
    }
}
