package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.ClientCreationRequest;
import us.telran.pawnshop.dto.ClientRealCreationRequest;
import us.telran.pawnshop.entity.Client;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    void addNewClient(ClientCreationRequest clientCreationRequest);

    void addNewRealClient(ClientRealCreationRequest clientRealCreationRequest);

    List<Client> getClients();

    Client getClientById(Long clientId);

    Client findClientBySsn(int ssn);

    void deleteClient(Long clientId);

    void updateClient(Long clientId,
                      String firstName,
                      String lastName,
                      LocalDate dateOfBirth,
                      String email,
                      String address
    );
}
