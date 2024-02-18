package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.dto.ClientCreationRequest;
import us.telran.pawnshop.dto.ClientRealCreationRequest;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.enums.ClientStatus;
import us.telran.pawnshop.repository.ClientRepository;
import us.telran.pawnshop.service.ClientService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    public final ClientRepository clientRepository;

    @Override
    @Transactional
    public void addNewClient(ClientCreationRequest clientCreationRequest) {

        Optional<Client> existClient = clientRepository.findClientByEmail(clientCreationRequest.getEmail());
        if (existClient.isPresent()) {
            throw new IllegalStateException("Email registered for client with id " 
                    + existClient.get().getClientId());
        }
        else {
            Client client = new Client();
            client.setFirstName(clientCreationRequest.getFirstName());
            client.setLastName(clientCreationRequest.getLastName());
            client.setDateOfBirth(clientCreationRequest.getDateOfBirth());
            client.generateSocialSecurityNumber();
            client.setEmail(clientCreationRequest.getEmail());
            client.setAddress(clientCreationRequest.getAddress());
            client.setStatus(ClientStatus.REGULAR);
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void addNewClientReal(ClientRealCreationRequest clientRealCreationRequest) {
        Client existClient = findClientBySsn(clientRealCreationRequest.getSocialSecurityNumber());
        if (existClient != null) {
            throw new IllegalStateException("Client registered with id "
                    + existClient.getClientId());
        }
        else {
            Client client = new Client();
            client.setFirstName(clientRealCreationRequest.getFirstName());
            client.setLastName(clientRealCreationRequest.getLastName());
            client.setDateOfBirth(clientRealCreationRequest.getDateOfBirth());
            client.setSocialSecurityNumber(clientRealCreationRequest.getSocialSecurityNumber());
            client.setEmail(clientRealCreationRequest.getEmail());
            client.setAddress(clientRealCreationRequest.getAddress());
            client.setStatus(ClientStatus.REGULAR);
            clientRepository.save(client);
        }
    }

    @Override
    public List<Client> getClients() { return clientRepository.findAll(); }

    @Override
    @Transactional
    public void updateClient(Long clientId,
                             String firstName,
                             String lastName,
                             LocalDate dateOfBirth,
                             String email,
                             String address
    ) {
        Client client = getClientById(clientId);

        if (firstName != null
                   && !firstName.isEmpty()
                   && !Objects.equals(client.getFirstName(), firstName)) {
            client.setFirstName(firstName);
        }

        if (lastName != null && !lastName.isEmpty()
                  && !Objects.equals(client.getLastName(), lastName)) {
            client.setLastName(lastName);
        }

        if (email != null
               && !email.isEmpty()
               && !Objects.equals(client.getEmail(), email)) {
            Optional<Client> clientOptional = clientRepository.findClientByEmail(email);
            if (clientOptional.isPresent()) {
                throw new IllegalStateException("Email registered");
            }
            client.setEmail(email);
        }
        if (dateOfBirth != null && !Objects.equals(client.getDateOfBirth(), dateOfBirth)) {
            client.setDateOfBirth(dateOfBirth);
        }
        client.setDateOfBirth(dateOfBirth);

        if (address != null && !address.isEmpty()
                && !Objects.equals(client.getAddress(), address)) {
            client.setAddress(address);
        }
    }

    @Override
    @Transactional
    public void deleteClient(Long clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (!exists) {
            throw new IllegalStateException("Client with id " + clientId + " doesn't exist");
        }
        clientRepository.deleteById(clientId);
    }

    @Override
    public Client getClientById(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            return clientOptional.get();
        }
        else {
            throw new IllegalStateException("Client with id " + clientId + " doesn't exist");
        }
    }

    @Override
    public Client findClientBySsn(int ssn) {
        Optional<Client> clientOptional = clientRepository
                .findClientBySsn(ssn);
        if (clientOptional.isPresent()) {
            return clientOptional.get();
        }
        else {
            throw new IllegalStateException("Client with ssn " + ssn + " doesn't exist");
        }
    }
    
    public Client findClientByEmail(String email) {
        Optional<Client> clientOptional = clientRepository
                .findClientByEmail(email);
        if (clientOptional.isPresent()) {
            return clientOptional.get();
        }
        else {
            throw new IllegalStateException("Client with email " + email + " doesn't exist");
        }
    }
}
