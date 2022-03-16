package it.cineca.GestUser.webapp.model;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "Utenti")
@Data
public class UtentiDto
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 5, max = 80, message = "{Size.Utenti.userId.Validation}")
    @NotNull(message = "{NotNull.Utenti.userId.Validation}")
    private String userId;

    @Size(min = 8, max = 80, message = "{Size.Utenti.password.Validation}")
    private String password;

    private String attivo = "Si";

}
