package us.telran.pawnshop.service;

import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.Client;

import java.util.List;

public interface ClientService {
    void addNewClient(Client client);

    List<Client> getClients();

    void deleteClient(Long clientId);

    void updateClient(Long clientId, String firstName, String lastName, String email);
}
