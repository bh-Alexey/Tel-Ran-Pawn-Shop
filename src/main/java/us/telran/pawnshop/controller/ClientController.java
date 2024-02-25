package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.ClientRealCreationRequest;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.service.ClientService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "pawn-shop/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping(value = "new")
    @Operation(summary = "ADD CLIENT", description = "Create and save clients to the DB. New clients will have \"Regular\" status")
    public void createNewClient(@RequestBody ClientRealCreationRequest clientRealCreationRequest) {
        clientService.addNewRealClient(clientRealCreationRequest);
    }

    @GetMapping(value = "show")
    @Operation(summary = "ALL CLIENTS", description = "Show all clients in DB")
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @PutMapping(path = "update/{clientId}")
    @Operation(summary = "EDIT CLIENT", description = "Edit client's personal information." +
            " Client with specified id can be modified and saved to the DB")
    public void updateClient(
            @PathVariable("clientId") Long clientId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address) {
        clientService.updateClient(clientId, firstName, lastName, dateOfBirth, email, address);
    }

    @DeleteMapping(path = "remove/{clientId}")
    @Operation(summary = "DELETE CLIENT", description = "Remove client with specified id from the DB")
    public void deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
    }

}
