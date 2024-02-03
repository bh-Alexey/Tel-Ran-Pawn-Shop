package us.telran.pawnshop.service;

import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.Client;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    void addNewClient(Client client);

    void addNewClientReal(Client client);

    List<Client> getClients();

    void deleteClient(Long clientId);

    void updateClient(Long clientId,
                      String firstName,
                      String lastName,
                      LocalDate dateOfBirth,
                      String email,
                      String address
    );
}
