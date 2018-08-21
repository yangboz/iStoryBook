/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import tech.smartkit.istorybook.blockchain.contracts.generated.Greeter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

@RestController
public class BlockChainController {

    protected Logger logger = Logger.getLogger(BlockChainController.class
            .getName());

    //@see:https://github.com/web3j/web3j-spring-boot-starter
    @Autowired
    private Web3j web3j;

//    @Autowired
//    private Admin admin;
    /**
     * Connecting to a node on the Ethereum network.
     */
    @RequestMapping("/blockchain/ethereum/connect/{node}")
    public void connect(@PathVariable("node") String nodeName) throws IOException {
        logger.info("blockchain-service connect() invoked, for "
                + nodeName);
        //
//        Web3j web3j = Web3j.build(new HttpService(
//                "https://rinkeby.infura.io/udBxuyCvHOqvmFhEEi4S"));  // FIXME: Enter your Infura token here;
        logger.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());

    }

    /**
     * Loading an Ethereum wallet file.
     */
    @RequestMapping("/blockchain/ethereum/load/{wallet}")
    public void loadWallet(@PathVariable("wallet") String walletFile){
        logger.info("blockchain-service loadWallet() invoked, for "
                + walletFile);
    }

    private Credentials getCredentials() throws IOException, CipherException {
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "kit7740321",
                        "/Users/yangboz/Desktop/EtherumWallet.json");
        logger.info("Credentials loaded");
        return credentials;
    }

    /**
     * Sending Ether from one address to another.
     */
    @RequestMapping("/blockchain/ethereum/send/{ether}")
    public void sendEther(@PathVariable("ether") float ether) throws Exception {
        logger.info("blockchain-service sendEther() invoked, for "
                + ether);
        Credentials credentials = getCredentials();
        // FIXME: Request some Ether for the Rinkeby test network at https://www.rinkeby.io/#faucet
        logger.info("Sending 1 Wei ("
                + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
        TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentials,
                "0x19e03255f667bdfd50a32722df860b1eeaf4d635",  // you can put any address here
                BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether
                .send();
        logger.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());
    }

    private Contract getContract(Credentials credentials ) throws Exception {
        logger.info("Deploying smart contract");
        Greeter contract = Greeter.deploy(
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
                "Hello blockchain world!").send();
        return contract;
    }
    /**
     * Deploying a smart contract to the network.
     */
    @RequestMapping("/blockchain/ethereum/deploy/{smartContract}")
    public void deploySmartContract(@PathVariable("smartContract") String smartContract) throws Exception {
        logger.info("blockchain-service deploySmartContract() invoked, for "
                + smartContract);

        Credentials credentials = getCredentials();
        Greeter contract = (Greeter) getContract(credentials);

        String contractAddress = contract.getContractAddress();
        logger.info("Smart contract deployed to address " + contractAddress);
        logger.info("View contract at https://rinkeby.etherscan.io/address/" + contractAddress);
        logger.info("Value stored in remote smart contract: " + contract.greet().send());
    }

    /**
     * Reading a value from the deployed smart contract.
     */
    @RequestMapping("/blockchain/ethereum/smartcontract/read/{value}")
    public void readSmartContract(@PathVariable("value") String value) throws Exception {
        logger.info("blockchain-service readSmartContract() invoked, for "
                + value);
        // Lets modify the value in our smart contract
        Credentials credentials = getCredentials();
        Greeter contract = (Greeter) getContract(credentials);

        TransactionReceipt transactionReceipt = contract.newGreeting("Well hello again").send();

        logger.info("New value stored in remote smart contract: " + contract.greet().send());
    }

    /**
     * Updating a value in the deployed smart contract.
     */
    @RequestMapping("/blockchain/ethereum/smartcontract/update/{node}")
    public void updateSmartContract(@PathVariable("value") String value) {
        logger.info("blockchain-service updateSmartContract() invoked, for "
                + value);
    }

    /**
     * Viewing an event logged by the smart contract.
     */
    @RequestMapping("/blockchain/connect/ethereum/smartcontract/{node}")
    public void viewSmartContractEvent(@PathVariable("event") String event) throws Exception {
        logger.info("blockchain-service viewSmartContractEvent() invoked, for "
                + event);

        Credentials credentials = getCredentials();
        Greeter contract = (Greeter) getContract(credentials);

        TransactionReceipt transactionReceipt = contract.newGreeting("Well hello again").send();
        // Events enable us to log specific events happening during the execution of our smart
        // contract to the blockchain. Index events cannot be logged in their entirety.
        // For Strings and arrays, the hash of values is provided, not the original value.
        // For further information, refer to https://docs.web3j.io/filters.html#filters-and-events
        for (Greeter.ModifiedEventResponse gEvent : contract.getModifiedEvents(transactionReceipt)) {
            logger.info("Modify event fired, previous value: " + gEvent.oldGreeting
                    + ", new value: " + gEvent.newGreeting);
            logger.info("Indexed event previous value: " + Numeric.toHexString(gEvent.oldGreetingIdx)
                    + ", new value: " + Numeric.toHexString(gEvent.newGreetingIdx));
        }
    }

}
