package it.cineca.GestUser.webapp.service;

import it.cineca.GestUser.webapp.model.UtentiDto;

import java.util.List;

public interface UtentiService
{
    public List<UtentiDto> SelTutti();

    public UtentiDto SelUser(String UserId);

    public void Save(UtentiDto utente);

    public void Delete(UtentiDto utente);

}
