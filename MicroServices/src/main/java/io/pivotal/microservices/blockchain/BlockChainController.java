package io.pivotal.microservices.blockchain;

import io.pivotal.microservices.accounts.Account;
import io.pivotal.microservices.accounts.AccountsController;
import io.pivotal.microservices.exceptions.AccountNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class BlockChainController {
    protected Logger logger = Logger.getLogger(BlockChainController.class
            .getName());

    /**
     * Connecting to a node on the Ethereum network.
     */
    @RequestMapping("/blockchain/ethereum/connect/{node}")
    public void connect(@PathVariable("node") String nodeName) {
        logger.info("blockchain-service connect() invoked, for "
                + nodeName);
    }

    /**
     * Loading an Ethereum wallet file.
     */
    @RequestMapping("/blockchain/ethereum/load/{wallet}")
    public void loadWallet(@PathVariable("wallet") String walletFile) {
        logger.info("blockchain-service loadWallet() invoked, for "
                + walletFile);
    }

    /**
     * Sending Ether from one address to another.
     */
    @RequestMapping("/blockchain/ethereum/send/{ether}")
    public void sendEther(@PathVariable("ether") float ether) {
        logger.info("blockchain-service sendEther() invoked, for "
                + ether);
    }

    /**
     * Deploying a smart contract to the network.
     */
    @RequestMapping("/blockchain/ethereum/deploy/{smartContract}")
    public void deploySmartContract(@PathVariable("smartContract") String smartContract) {
        logger.info("blockchain-service deploySmartContract() invoked, for "
                + smartContract);
    }

    /**
     * Reading a value from the deployed smart contract.
     */
    @RequestMapping("/blockchain/ethereum/smartcontract/read/{value}")
    public void readSmartContract(@PathVariable("value") String value) {
        logger.info("blockchain-service readSmartContract() invoked, for "
                + value);
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
    public void viewSmartContractEvent(@PathVariable("event") String event) {
        logger.info("blockchain-service viewSmartContractEvent() invoked, for "
                + event);
    }

}
