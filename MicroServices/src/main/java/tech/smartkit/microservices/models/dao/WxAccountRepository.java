/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models.dao;

import org.springframework.data.repository.CrudRepository;
import tech.smartkit.microservices.models.Account;
import tech.smartkit.microservices.models.WxUserInfo;

import java.util.List;

public interface WxAccountRepository extends CrudRepository<WxUserInfo,Long> {

    /**
     * Find accounts whose nickname contains the specified string
     *
     * @param nickName
     *            Any alphabetic string.
     * @return The list of matching accounts - always non-null, but may be
     *         empty.
     */
    public List<WxUserInfo> findByNickName(String nickName);
}
