package com.project.journalApp.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int status;

    // Constructor
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }


}
