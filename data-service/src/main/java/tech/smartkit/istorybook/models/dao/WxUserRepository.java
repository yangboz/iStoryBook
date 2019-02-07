/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models.dao;

import org.springframework.data.repository.CrudRepository;
import tech.smartkit.istorybook.models.WxUser;

import java.util.List;
import java.util.Optional;

public interface WxUserRepository extends CrudRepository<WxUser, Long> {

    /**
     * Find accounts whose nickname contains the specified string
     *
     * @param nickName
     *            Any alphabetic string.
     * @return The list of matching accounts - always non-null, but may be
     *         empty.
     */
    public List<WxUser> findByNickName(String nickName);

    //
    public Optional<WxUser> findById(Long id);
    //
    public Optional<WxUser> findByOpenid(String oid);
}
