/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models.dao;

import org.springframework.data.repository.CrudRepository;
import tech.smartkit.microservices.models.WxUserInfo;

public interface WxAccountRepository extends CrudRepository<WxUserInfo,Long> {
}
