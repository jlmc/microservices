package org.jcosta.bookstore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Error {

    private String message;
    private String devMessage;

    Error() {}

    private Error(String message, String devMessage) {
        this.message = message;
        this.devMessage = devMessage;
    }

    static Error of (String message, String devMessage) {
        return new Error(message, devMessage);
    }

    public String getDevMessage() {
        return devMessage;
    }

    public String getMessage() {
        return message;
    }
}
