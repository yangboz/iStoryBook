/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models;

import org.springframework.data.repository.CrudRepository;

public interface WxAccountRepository extends CrudRepository<WxUserInfo,Long> {
}
