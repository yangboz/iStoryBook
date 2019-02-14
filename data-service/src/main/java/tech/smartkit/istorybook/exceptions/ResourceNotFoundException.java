/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Client error handlers
//http://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration
//http://www.springboottutorial.com/spring-boot-exception-handling-for-rest-services
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String exception) {
        super(exception);
    }
}
