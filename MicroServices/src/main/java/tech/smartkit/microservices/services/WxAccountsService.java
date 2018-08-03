/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tech.smartkit.microservices.controllers.AccountsController;
import tech.smartkit.microservices.exceptions.AccountNotFoundException;
import tech.smartkit.microservices.models.WxUserInfo;
import tech.smartkit.microservices.models.dao.WxAccountRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class WxAccountsService {

    @Autowired
    protected WxAccountRepository wxAccountRepository;

    protected Logger logger = Logger.getLogger(WxAccountsService.class
            .getName());
//@Autowired
//    public WxAccountsService(WxAccountRepository wxAccountRepository){
//        this.wxAccountRepository = wxAccountRepository;
//    }

    public Iterable listAll(){
        logger.info("List of accounts:");
        Iterable accountList = wxAccountRepository.findAll();
        return accountList;
    }

    public List<WxUserInfo> byNickName(String nickName){
        logger.info("accounts-service byOwner() invoked: "
                + wxAccountRepository.getClass().getName() + " for "
                + nickName);
        List<WxUserInfo> accounts = wxAccountRepository.findByNickName(nickName);
        logger.info("accounts-service byOwner() found: " + accounts);

        if (accounts == null || accounts.size() == 0)
            throw new AccountNotFoundException(nickName);
        else {
            return accounts;
        }
    }

    public int counts() {
        logger.info("accounts-service counts() invoked: ");
        List<WxUserInfo> wxUserInfos = (List<WxUserInfo>) wxAccountRepository.findAll();
        logger.info("accounts-service counts() found: " + wxUserInfos.size());
        return wxUserInfos.size();
    }

    public WxUserInfo save(WxUserInfo wxUserInfo ){
//			@RequestParam("userInfo") WxUserInfo wxUserInfo) {
        logger.info("accounts-service save() invoked: ");
        //if not existed?
        List<WxUserInfo> find = wxAccountRepository.findByNickName(wxUserInfo.getNickName());
        WxUserInfo saved = null;
        if(find.size()==0){
            saved = wxAccountRepository.save(wxUserInfo);
        }{
            logger.info("accounts-service save() already existed: " + find.toString());
        }
        logger.info("accounts-service save() result: " + saved);
        return saved;
    }
}
