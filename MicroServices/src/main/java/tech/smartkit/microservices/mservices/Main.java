/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.mservices;

import tech.smartkit.microservices.mservices.accounts.AccountsServer;
import tech.smartkit.microservices.mservices.blockchain.BlockChainServer;
import tech.smartkit.microservices.mservices.carts.CartServer;
import tech.smartkit.microservices.mservices.orders.OrderServer;
import tech.smartkit.microservices.mservices.products.ProductServer;
import tech.smartkit.microservices.mservices.registration.RegistrationServer;
import tech.smartkit.microservices.mservices.web.WebServer;

/**
 * Allow the servers to be invoked from the command-line. The jar is built with
 * this as the <code>Main-Class</code> in the jar's <code>MANIFEST.MF</code>.
 * 
 * @author Paul Chapman
 */
public class Main {

	public static void main(String[] args) {

		String serverName = "NO-VALUE";

		switch (args.length) {
		case 2:
			// Optionally set the HTTP port to listen on, overrides
			// value in the <server-name>-server.yml file
			System.setProperty("server.port", args[1]);
			// Fall through into ..

		case 1:
			serverName = args[0].toLowerCase();
			break;

		default:
			usage();
			return;
		}

		if (serverName.equals("registration") || serverName.equals("reg")) {
			RegistrationServer.main(args);
		} else if (serverName.equals("account")) {
			AccountsServer.main(args);
		} else if (serverName.equals("web")) {
			WebServer.main(args);
		} else if (serverName.equals("blockchain")) {
			BlockChainServer.main(args);
		} else if (serverName.equals("product")) {
			ProductServer.main(args);
		} else if (serverName.equals("order")) {
			OrderServer.main(args);
		} else if (serverName.equals("cart")) {
			CartServer.main(args);
		}
		else {
			System.out.println("Unknown server type: " + serverName);
			usage();
		}
	}

	protected static void usage() {
		System.out.println("Usage: java -jar ... <server-name> [server-port]");
		System.out.println(
				"     where server-name is 'reg', 'registration', " + "'account', 'web', 'product', 'order', 'cart' and server-port > 1024");
	}
}
