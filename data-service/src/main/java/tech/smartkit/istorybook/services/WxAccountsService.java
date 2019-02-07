/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.smartkit.istorybook.exceptions.AccountNotFoundException;
import tech.smartkit.istorybook.models.WxUser;
import tech.smartkit.istorybook.models.dao.WxUserRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class WxAccountsService {

    @Autowired
    protected WxUserRepository wxUserRepository;

    protected Logger logger = Logger.getLogger(WxAccountsService.class
            .getName());
//@Autowired
//    public WxAccountsService(WxUserRepository wxUserRepository){
//        this.wxUserRepository = wxUserRepository;
//    }

    public Iterable listAll(){
        logger.info("List of accounts:");
        Iterable accountList = wxUserRepository.findAll();
        return accountList;
    }

    public List<WxUser> byNickName(String nickName){
        logger.info("accounts-service byOwner() invoked: "
                + wxUserRepository.getClass().getName() + " for "
                + nickName);
        List<WxUser> accounts = wxUserRepository.findByNickName(nickName);
        logger.info("accounts-service byOwner() found: " + accounts);

        if (accounts == null || accounts.size() == 0)
            throw new AccountNotFoundException(nickName);
        else {
            return accounts;
        }
    }

    public WxUser findOne(Long id){
        return wxUserRepository.findById(id).get();
    }

    public int counts() {
        logger.info("accounts-service counts() invoked: ");
        List<WxUser> wxUserInfos = (List<WxUser>) wxUserRepository.findAll();
        logger.info("accounts-service counts() found: " + wxUserInfos.size());
        return wxUserInfos.size();
    }

    public WxUser save(WxUser wxUserInfo ){
//			@RequestParam("userInfo") WxUser wxUserInfo) {
        logger.info("accounts-service save() invoked: ");
        //if not existed?
        List<WxUser> find = wxUserRepository.findByNickName(wxUserInfo.getNickName());
        WxUser saved = null;
        if(find.size()==0){
            saved = wxUserRepository.save(wxUserInfo);
        }{
            logger.info("accounts-service save() already existed: " + find.toString());
        }
        logger.info("accounts-service save() result: " + saved);
        return saved;
    }
}
