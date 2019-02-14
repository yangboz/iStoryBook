package tech.smartkit.istorybook.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ErrorDetails implements Serializable{

    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
