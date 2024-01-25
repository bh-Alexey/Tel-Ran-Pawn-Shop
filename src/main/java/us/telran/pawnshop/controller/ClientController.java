package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.service.ClientService;

import java.util.List;

@RestController
@RequestMapping(path = "api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping(value = "new")
    public void createNewClient(@RequestBody Client client) {
        clientService.addNewClient(client);
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @DeleteMapping(path = "remove/{clientId}")
    public void deleteClient(@PathVariable("clientId") Long studentId) {
        clientService.deleteClient(studentId);
    }

    @PutMapping(path = "update/{clientId}")
    public void updateClient(
            @PathVariable("clientId") Long clientId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {
        clientService.updateClient(clientId, firstName, lastName, email);
    }
}
