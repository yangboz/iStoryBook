/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.smartkit.istorybook.exceptions.AccountNotFoundException;
import tech.smartkit.istorybook.models.WxAccount;
import tech.smartkit.istorybook.models.dao.WxAccountRepository;

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

    public List<WxAccount> byNickName(String nickName){
        logger.info("accounts-service byOwner() invoked: "
                + wxAccountRepository.getClass().getName() + " for "
                + nickName);
        List<WxAccount> accounts = wxAccountRepository.findByNickName(nickName);
        logger.info("accounts-service byOwner() found: " + accounts);

        if (accounts == null || accounts.size() == 0)
            throw new AccountNotFoundException(nickName);
        else {
            return accounts;
        }
    }

    public WxAccount findOne(Long id){
        return wxAccountRepository.findById(id);
    }

    public int counts() {
        logger.info("accounts-service counts() invoked: ");
        List<WxAccount> wxUserInfos = (List<WxAccount>) wxAccountRepository.findAll();
        logger.info("accounts-service counts() found: " + wxUserInfos.size());
        return wxUserInfos.size();
    }

    public WxAccount save(WxAccount wxUserInfo ){
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
        return saved;
    }
}
