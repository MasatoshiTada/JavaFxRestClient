package com.example.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionDto {
    @JsonProperty("exception_class")
    private String exceptionClass;
    private String[] messages;

    /**
     * @return the exceptionClass
     */
    public String getExceptionClass() {
        return exceptionClass;
    }

    /**
     * @param exceptionClass the exceptionClass to set
     */
    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    /**
     * @return the messages
     */
    public String[] getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(String[] messages) {
        this.messages = messages;
    }

}
