/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tech.smartkit.istorybook.exceptions.AccountNotFoundException;
import tech.smartkit.istorybook.models.Account;
import tech.smartkit.istorybook.models.StoryBookPage;
import tech.smartkit.istorybook.models.WxUser;
import tech.smartkit.istorybook.models.dao.AccountRepository;
import tech.smartkit.istorybook.models.dao.WxUserRepository;
import tech.smartkit.istorybook.services.WordPressService;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author yangboz
 *
 * @ref: https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
 */
@RestController
@RequestMapping(value ="/account")
@Api(value="AccountsController", description="Operations pertaining to accounts in iStoryBook")
public class AccountsController {

	protected Logger logger = Logger.getLogger(AccountsController.class
			.getName());
	@Autowired
	protected AccountRepository accountRepository;
//	@Autowired
//	protected WxUserRepository wxUserRepository;
	@Autowired
	WordPressService wordPressService;
//	/**
//	 * Create an instance plugging in the respository of Accounts.
//	 *
//	 *            An account repository implementation.
//	 */
//	@Autowired
//	public AccountsController(AccountRepository wxUserRepository) {
//		this.accountRepository = accountRepository;
//		logger.info("AccountRepository autowired.");
//	}
//	public AccountsController() {
//		logger.info("AccountRepository says system has "
//				+ accountRepository.countAccounts() + " accounts");
//	}
	//https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
	@ApiOperation(value = "View a list of available accounts", response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(value = "/", method= RequestMethod.GET, produces = "application/json")
	public Iterable list(){
		logger.info("List of accounts:");
		Iterable accountList = accountRepository.findAll();
		return accountList;
	}

	/**
	 GET / - List of accounts
	 POST / - Add product - required : String name , String groupId, String userId
	 GET /{id} - View product
	 POST /{id} - Update product
	 DELETE /{id}/ - Delete one of accounts
	 *
	 */
	/**
	 * Fetch an account with the specified account number.
	 *
	 * @param id
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws AccountNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/{id}")
	public Account byId(@PathVariable("id") String id) {

		logger.info("accounts-service byId() invoked: " + id);
		Account account = accountRepository.findByNumber(id);
		logger.info("accounts-service byId() found: " + account);

		if (account == null)
			throw new AccountNotFoundException(id);
		else {
			return account;
		}
	}

	/**
	 * Fetch accounts with the specified name. A partial case-insensitive match
	 * is supported. So <code>http://.../accounts/owner/a</code> will find any
	 * accounts with upper or lower case 'a' in their name.
	 * 
	 * @param owner
	 * @return A non-null, non-empty set of accounts.
	 * @throws AccountNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/o/{owner}")
	public List<Account> byOwner(@PathVariable("owner") String owner) {
		logger.info("accounts-service byOwner() invoked: "
				+ accountRepository.getClass().getName() + " for "
				+ owner);

		List<Account> accounts = accountRepository.findByOwnerContainingIgnoreCase(owner);
		logger.info("accounts-service byOwner() found: " + accounts);

		if (accounts == null || accounts.size() == 0)
			throw new AccountNotFoundException(owner);
		else {
			return accounts;
		}
	}

	/**
	 * Fetch an accounts count number.
	 *
	 * @return The total counts.
	 */
	@RequestMapping("/counts")
	public int counts() {

		logger.info("accounts-service counts() invoked: ");
		List<Account> wxUserInfos = (List<Account>) accountRepository.findAll();
		logger.info("accounts-service counts() found: " + wxUserInfos.size());
		return wxUserInfos.size();
	}

	/**
	 * Save an accounts info.
	 *
	 * @return The update save.
	 *
	 * @see: https://www.leveluplunch.com/java/tutorials/014-post-json-to-spring-rest-webservice/
	 */
	@RequestMapping(value="/save/",method = RequestMethod.POST
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> save(
			@RequestBody Account account ) throws WpApiParsedException {
//			@RequestParam("userInfo") WxUser wxUserInfo) {
		logger.info("accounts-service save() invoked: ");
		//if not existed?
		List<Account> find = accountRepository.findByOwnerContainingIgnoreCase(account.getOwner());
		Account saved = null;
		if(find==null){
			saved = accountRepository.save(account);
		}{
			logger.info("accounts-service save() already existed: " + find.toString());
		}
		logger.info("accounts-service save() result: " + saved);
		return new ResponseEntity<Account>(saved, HttpStatus.OK);
	}

	@RequestMapping("/f/{oid}")
	public List<Account> findByOpenid(@PathVariable("oid") String oid) {
		List<Account> find = accountRepository.findByOwnerContainingIgnoreCase(oid);
		logger.info("accounts-service findByOpenid result: " + find);
		return find;
	}
}
