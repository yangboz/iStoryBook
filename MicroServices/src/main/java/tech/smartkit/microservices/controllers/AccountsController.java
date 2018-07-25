/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.controllers;

import java.util.List;
import java.util.logging.Logger;

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
 */
@RestController
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

	/**
	 * Fetch an account with the specified account number.
	 * 
	 * @param accountNumber
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws AccountNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/accounts/{accountNumber}")
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
	@RequestMapping("/accounts/owner/{name}")
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
	@RequestMapping(value="/accounts/save/",method = { RequestMethod.POST })
	public WxUserInfo save(@RequestParam("userInfo") WxUserInfo wxUserInfo) {
		logger.info("accounts-service save() invoked: ");
		WxUserInfo saved = wxAccountRepository.save(wxUserInfo);
		logger.info("accounts-service save() result: " + saved);
		return saved;
	}
}
