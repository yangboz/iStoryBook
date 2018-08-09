/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.accounts;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.smartkit.microservices.controllers.AccountsController;
import tech.smartkit.microservices.exceptions.AccountNotFoundException;
import tech.smartkit.microservices.models.WxAccount;

public abstract class AbstractAccountControllerTests {

	protected static final String ACCOUNT_1 = "123456789";
	protected static final String ACCOUNT_1_NAME = "Keri Lee";

	@Autowired
	AccountsController accountController;

	@Test
	public void validAccountNumber() {
		Logger.getGlobal().info("Start validAccountNumber test");
		List<WxAccount> accounts = accountController.byNickName(ACCOUNT_1_NAME);

		Assert.assertNotNull(accounts.size());
		Assert.assertEquals(ACCOUNT_1, accounts.get(0).getId());
		Assert.assertEquals(ACCOUNT_1_NAME, accounts.get(0).getNickName());
		Logger.getGlobal().info("End validAccount test");
	}
	
	@Test
	public void validAccountOwner() {
		Logger.getGlobal().info("Start validAccount test");
		List<WxAccount> accounts = accountController.byNickName(ACCOUNT_1_NAME);
		Logger.getGlobal().info("In validAccount test");

		Assert.assertNotNull(accounts);
		Assert.assertEquals(1, accounts.size());

		WxAccount account = accounts.get(0);
		Assert.assertEquals(ACCOUNT_1, account.getId());
		Assert.assertEquals(ACCOUNT_1_NAME, account.getNickName());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void validAccountOwnerMatches1() {
		Logger.getGlobal().info("Start validAccount test");
		List<WxAccount> accounts = accountController.byNickName("Keri");
		Logger.getGlobal().info("In validAccount test");

		Assert.assertNotNull(accounts);
		Assert.assertEquals(1, accounts.size());

		WxAccount account = accounts.get(0);
		Assert.assertEquals(ACCOUNT_1, account.getId());
		Assert.assertEquals(ACCOUNT_1_NAME, account.getNickName());
		Logger.getGlobal().info("End validAccount test");
	}
	
	@Test
	public void validAccountOwnerMatches2() {
		Logger.getGlobal().info("Start validAccount test");
		List<WxAccount> accounts = accountController.byNickName("keri");
		Logger.getGlobal().info("In validAccount test");

		Assert.assertNotNull(accounts);
		Assert.assertEquals(1, accounts.size());

		WxAccount account = accounts.get(0);
		Assert.assertEquals(ACCOUNT_1, account.getId());
		Assert.assertEquals(ACCOUNT_1_NAME, account.getNickName());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void invalidAccountNumber() {
		try {
			accountController.byId("10101010");
			Assert.fail("Expected an AccountNotFoundException");
		} catch (AccountNotFoundException e) {
			// Worked!
		}
	}

	@Test
	public void invalidAccountName() {
		try {
			accountController.byNickName("Fred Smith");
			Assert.fail("Expected an AccountNotFoundException");
		} catch (AccountNotFoundException e) {
			// Worked!
		}
	}
}
