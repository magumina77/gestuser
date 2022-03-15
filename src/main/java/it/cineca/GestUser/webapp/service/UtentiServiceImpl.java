package it.cineca.GestUser.webapp.service;

import it.cineca.GestUser.webapp.model.UtentiDto;
import it.cineca.GestUser.webapp.repository.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UtentiServiceImpl implements UtentiService
{

    @Autowired
    UtentiRepository utentiRepository;

    @Override
    public List<UtentiDto> SelTutti()
    {
        return utentiRepository.findAll();
    }

    @Override
    public UtentiDto SelUser(String UserId)
    {
        return utentiRepository.findByUserId(UserId);
    }


    @Override
    public void Save(UtentiDto utente)
    {
        utentiRepository.save(utente);
    }

    @Override
    public void Delete(UtentiDto utente)
    {
        utentiRepository.delete(utente);
    }

}
