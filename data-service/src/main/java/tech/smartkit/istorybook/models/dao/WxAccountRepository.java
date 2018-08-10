/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models.dao;

import org.springframework.data.repository.CrudRepository;
import tech.smartkit.istorybook.models.WxAccount;

import java.util.List;

public interface WxAccountRepository extends CrudRepository<WxAccount,Long> {

    /**
     * Find accounts whose nickname contains the specified string
     *
     * @param nickName
     *            Any alphabetic string.
     * @return The list of matching accounts - always non-null, but may be
     *         empty.
     */
    public List<WxAccount> findByNickName(String nickName);

    //
    public WxAccount findById(Long id);
}
