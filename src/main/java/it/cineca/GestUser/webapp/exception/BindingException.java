package it.cineca.GestUser.webapp.exception;

import lombok.*;

@Getter
@Setter
public class BindingException  extends Exception
{
    private static final long serialVersionUID = -5198072630604345819L;

    private String messaggio;

    public BindingException()
    {
        super();
    }

    public BindingException(String messaggio)
    {
        super(messaggio);
        this.messaggio = messaggio;
    }

}
