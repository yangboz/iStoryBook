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
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tech.smartkit.microservices.exceptions.AccountNotFoundException;
import tech.smartkit.microservices.models.Account;
import tech.smartkit.microservices.models.AccountRepository;
import tech.smartkit.microservices.models.WxAccountRepository;
import tech.smartkit.microservices.models.WxUserInfo;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 *
 * @ref: https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
 */
@RestController
@RequestMapping("/accounts")
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
//	@Autowired
//	public AccountsController(AccountRepository accountRepository) {
//		this.accountRepository = accountRepository;
//
//		logger.info("AccountRepository says system has "
//				+ accountRepository.countAccounts() + " accounts");
//	}
	public AccountsController() {
		logger.info("AccountRepository says system has "
				+ accountRepository.countAccounts() + " accounts");
	}
	//https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
	@ApiOperation(value = "View a list of available accounts", response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
	public Iterable list(){
		Iterable accountList = accountRepository.findAll();
		return accountList;
	}
	/**
	 * Fetch an account with the specified account number.
	 *
	 * @param accountNumber
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws AccountNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/{accountNumber}")
	public Account byNumber(@PathVariable("accountNumber") String accountNumber) {

		logger.info("accounts-service byNumber() invoked: " + accountNumber);
		Account account = accountRepository.findByNumber(accountNumber);
		logger.info("accounts-service byNumber() found: " + account);

		if (account == null)
			throw new AccountNotFoundException(accountNumber);
		else {
			return account;
		}
	}

	/**
	 * Fetch accounts with the specified name. A partial case-insensitive match
	 * is supported. So <code>http://.../accounts/owner/a</code> will find any
	 * accounts with upper or lower case 'a' in their name.
	 * 
	 * @param partialName
	 * @return A non-null, non-empty set of accounts.
	 * @throws AccountNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/owner/{name}")
	public List<Account> byOwner(@PathVariable("name") String partialName) {
		logger.info("accounts-service byOwner() invoked: "
				+ accountRepository.getClass().getName() + " for "
				+ partialName);

		List<Account> accounts = accountRepository
				.findByOwnerContainingIgnoreCase(partialName);
		logger.info("accounts-service byOwner() found: " + accounts);

		if (accounts == null || accounts.size() == 0)
			throw new AccountNotFoundException(partialName);
		else {
			return accounts;
		}
	}

	/**
	 * Fetch an accounts count number.
	 *
	 * @return The total counts.
	 */
	@RequestMapping("/accounts/counts")
	public int counts() {

		logger.info("accounts-service counts() invoked: ");
		int counts = accountRepository.countAccounts();
		logger.info("accounts-service counts() found: " + counts);
		return counts;
	}

	/**
	 * Save an accounts info.
	 *
	 * @return The update save.
	 */
	@RequestMapping(value="/save/",method = { RequestMethod.POST })
	public WxUserInfo save(@RequestParam("userInfo") WxUserInfo wxUserInfo) {
		logger.info("accounts-service save() invoked: ");
		WxUserInfo saved = wxAccountRepository.save(wxUserInfo);
		logger.info("accounts-service save() result: " + saved);
		return saved;
	}
}
