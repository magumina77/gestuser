package it.cineca.GestUser.webapp.controller;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class InfoMsg {
    public LocalDate data;

    public String message;
}
