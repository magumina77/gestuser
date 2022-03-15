package it.cineca.GestUser.webapp.repository;

import it.cineca.GestUser.webapp.model.UtentiDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtentiRepository extends JpaRepository<UtentiDto, Long>
{
    public UtentiDto findByUserId(String UserId);
}
