package it.cineca.GestUser.webapp.exception;

import lombok.*;

@Getter
@Setter
public class NotFoundException  extends Exception
{
    private static final long serialVersionUID = -8729169303699924451L;

    private String messaggio = "Elemento Ricercato Non Trovato!";

    public NotFoundException()
    {
        super();
    }

    public NotFoundException(String messaggio)
    {
        super(messaggio);
        this.messaggio = messaggio;
    }
}
