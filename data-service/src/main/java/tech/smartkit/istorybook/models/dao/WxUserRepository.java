/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.smartkit.istorybook.models.WxUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface WxUserRepository extends CrudRepository<WxUser, Long> {

    /**
     * Find accounts whose nickname contains the specified string
     *
     * @param nickName
     *            Any alphabetic string.
     * @return The list of matching accounts - always non-null, but may be
     *         empty.
     */
    List<WxUser> findByNickName(String nickName);
    //
    Optional<WxUser> findById(Long id);
    //
    Optional<WxUser> findByOpenid(String oid);
}
