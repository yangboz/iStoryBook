/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.controllers;

import java.util.List;
import java.util.logging.Logger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tech.smartkit.microservices.exceptions.AccountNotFoundException;
import tech.smartkit.microservices.models.Account;
import tech.smartkit.microservices.models.WxAccount;
import tech.smartkit.microservices.models.dao.AccountRepository;
import tech.smartkit.microservices.models.dao.WxAccountRepository;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 *
 * @ref: https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
 */
@RestController
@RequestMapping(value ="/account")
@Api(value="iStoryBookAccounts", description="Operations pertaining to accounts in iStoryBook")
public class AccountsController {

	protected Logger logger = Logger.getLogger(AccountsController.class
			.getName());
	@Autowired
	protected AccountRepository accountRepository;
	@Autowired
	protected WxAccountRepository wxAccountRepository;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 *
	 *            An account repository implementation.
	 */
	@Autowired
	public AccountsController(WxAccountRepository wxAccountRepository) {
		this.wxAccountRepository = wxAccountRepository;
		logger.info("AccountRepository autowired.");
	}
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
	 * @param nickName
	 * @return A non-null, non-empty set of accounts.
	 * @throws AccountNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/nickName/{nickName}")
	public List<WxAccount> byNickName(@PathVariable("nickName") String nickName) {
		logger.info("accounts-service byOwner() invoked: "
				+ accountRepository.getClass().getName() + " for "
				+ nickName);

		List<WxAccount> accounts = wxAccountRepository.findByNickName(nickName);
		logger.info("accounts-service byOwner() found: " + accounts);

		if (accounts == null || accounts.size() == 0)
			throw new AccountNotFoundException(nickName);
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
		List<WxAccount> wxUserInfos = (List<WxAccount>) wxAccountRepository.findAll();
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
	public ResponseEntity<WxAccount> save(
			@RequestBody WxAccount wxUserInfo ){
//			@RequestParam("userInfo") WxAccount wxUserInfo) {
		logger.info("accounts-service save() invoked: ");
		//if not existed?
		List<WxAccount> find = wxAccountRepository.findByNickName(wxUserInfo.getNickName());
		WxAccount saved = null;
		if(find.size()==0){
			saved = wxAccountRepository.save(wxUserInfo);
		}{
			logger.info("accounts-service save() already existed: " + find.toString());
		}
		logger.info("accounts-service save() result: " + saved);
		return new ResponseEntity<WxAccount>(saved, HttpStatus.OK);
	}
}
