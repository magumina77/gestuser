package it.cineca.GestUser.webapp.exception;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class ErrorResponse
{
    private LocalDate date;
    private int code;
    private String message;
}
