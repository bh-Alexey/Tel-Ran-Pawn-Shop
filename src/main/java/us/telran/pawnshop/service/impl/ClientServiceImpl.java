package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.repository.ClientRepository;
import us.telran.pawnshop.service.ClientService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    public final ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public void addNewClient(Client client) {
        Optional<Client> clientOptional = clientRepository.findClientByEmail(client.getEmail());
        if (clientOptional.isPresent()) {
            throw new IllegalStateException("Email registered");
        }
        clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (!exists) {
            throw new IllegalStateException("Client with id " + clientId + " doesn't exist");
        }
        clientRepository.deleteById(clientId);
    }

    @Override
    @Transactional
    public void updateClient(Long clientId,
                              String firstName,
                              String lastName,
                              String email) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException("Client with id " + clientId + " doesn't exist"));

        if (firstName != null && !firstName.isEmpty() && !Objects.equals(client.getFirstName(), firstName)) {
            client.setFirstName(firstName);
        }

        if (lastName != null && !lastName.isEmpty() && !Objects.equals(client.getLastName(), lastName)) {
            client.setLastName(lastName);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(client.getEmail(), email)) {
            Optional<Client> clientOptional = clientRepository.findClientByEmail(email);
            if (clientOptional.isPresent()) {
                throw new IllegalStateException("Email registered");
            }
            client.setEmail(email);
        }
    }
}
