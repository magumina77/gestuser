package it.cineca.GestUser.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.cineca.GestUser.webapp.exception.BindingException;
import it.cineca.GestUser.webapp.exception.ErrorResponse;
import it.cineca.GestUser.webapp.exception.NotFoundException;
import it.cineca.GestUser.webapp.model.UtentiDto;
import it.cineca.GestUser.webapp.service.UtentiService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/utenti")
@Log
@Tag(name = "UtentiController", description = "Controller per la gestione delle operazioni sugli utenti")
public class UtentiController {
    @Autowired
    UtentiService utentiService;

    @Autowired
    private ResourceBundleMessageSource errMessage;

    @Operation(summary = "Ricerca Utenti", description = "Restituisce tutti gli utenti presenti in anagrafica", tags = {"UtentiDto"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elenco utenti")
    })
    @GetMapping(value = "/cerca/tutti")
    public List<UtentiDto> getAllUser() {
        log.info("Otteniamo tutti gli utenti");

        return utentiService.SelTutti();
    }

    @Operation(summary = "Ricerca Utente", description = "Restituisce l'utente in anagrafica in base all'identificativo fornito se non viene trovato restituisce un codice di errore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente trovato", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UtentiDto.class))),
            @ApiResponse(responseCode = "404", description = "Utente non trovato", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/cerca/userid/{userId}")
    @SneakyThrows
    public UtentiDto getUtente(@PathVariable("userId") String UserId) {
        log.info("Otteniamo l'utente " + UserId);

        UtentiDto utente = utentiService.SelUser(UserId);

        if (utente == null) {
            String ErrMsg = String.format("L'utente %s non e' stato trovato!", UserId);

            log.warning(ErrMsg);

            throw new NotFoundException(ErrMsg);
        }

        return utente;
    }

    // ------------------- INSERIMENTO / MODIFICA UTENTE ------------------------------------
    @Operation(summary = "Inserisce/Modifica Utente", description = "Inserisce o modifica un utente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente Creato/Aggiornato correttamente", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InfoMsg.class))),
            @ApiResponse(responseCode = "400", description = "Errore nei dati forniti", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/inserisci")
    @SneakyThrows
    public ResponseEntity<InfoMsg> addNewUser(@Valid @RequestBody UtentiDto utente,
                                              BindingResult bindingResult) {

        UtentiDto checkUtente = utentiService.SelUser(utente.getUserId());

        if (checkUtente != null) {
            utente.setId(checkUtente.getId());
            log.info("Modifica Utente");
        } else {
            log.info("Inserimento Nuovo Utente");
        }

        if (bindingResult.hasErrors()) {
            String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

            log.warning(MsgErr);

            throw new BindingException(MsgErr);
        }

        utente.setPassword(utente.getPassword());
        utentiService.Save(utente);

        return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
                String.format("Inserimento Utente %s Eseguita Con Successo", utente.getUserId())), HttpStatus.CREATED);
    }

    // ------------------- ELIMINAZIONE UTENTE ------------------------------------
    @Operation(summary = "Elimina un utente",description = "Elimina l'utente corrispondente all'identificativo passato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "utente eliminato con successo",content = @Content),
            @ApiResponse(responseCode = "404",description = "utente non trovato",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping(value = "/elimina/{id}")
    @SneakyThrows
    public ResponseEntity<?> deleteUser(@PathVariable("id") String UserId) {
        log.info("Eliminiamo l'utente con id " + UserId);

        UtentiDto utente = utentiService.SelUser(UserId);

        if (utente == null) {
            String MsgErr = String.format("Utente %s non presente in anagrafica! ", UserId);

            log.warning(MsgErr);

            throw new NotFoundException(MsgErr);
        }

        utentiService.Delete(utente);

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione Utente " + UserId + " Eseguita Con Successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }
}
