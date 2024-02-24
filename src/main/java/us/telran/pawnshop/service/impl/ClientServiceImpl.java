package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void addNewClient(ClientCreationRequest clientCreationRequest) {
        clientRepository.findClientByEmail(clientCreationRequest.getEmail())
                .ifPresent(client -> {
                    throw new EntityExistsException("Email registered for client with id "
                            + client.getClientId());
                });

        Client client = createClient(clientCreationRequest);
        clientRepository.save(client);
    }

    private Client createClient(ClientCreationRequest request) {
        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setDateOfBirth(request.getDateOfBirth());
        client.generateSocialSecurityNumber();
        client.setEmail(request.getEmail());
        client.setAddress(request.getAddress());
        client.setStatus(ClientStatus.REGULAR);
        return client;
    }

    @Override
    @Transactional
    public void addNewRealClient(ClientRealCreationRequest clientRealCreationRequest) {
        clientRepository.findClientBySsn(clientRealCreationRequest.getSocialSecurityNumber())
                .ifPresent(client -> {
                    throw new EntityExistsException("Client already registered with id " + client.getClientId());
                });

        Client client = createRealClient(clientRealCreationRequest);
        clientRepository.save(client);
    }
    private Client createRealClient(ClientRealCreationRequest clientRealCreationRequest) {
        Client client = new Client();
        client.setFirstName(clientRealCreationRequest.getFirstName());
        client.setLastName(clientRealCreationRequest.getLastName());
        client.setDateOfBirth(clientRealCreationRequest.getDateOfBirth());
        client.setSocialSecurityNumber(clientRealCreationRequest.getSocialSecurityNumber());
        client.setEmail(clientRealCreationRequest.getEmail());
        client.setAddress(clientRealCreationRequest.getAddress());
        client.setStatus(ClientStatus.REGULAR);
        return client;
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
                throw new EntityExistsException("Email registered");
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
        clientRepository.findById(clientId)
                .ifPresentOrElse(
                    clientRepository::delete,
                    () -> { throw new EntityNotFoundException("Client with id " + clientId + " doesn't exist"); }
                );
    }

    @Override
    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " doesn't exist"));
    }

    @Override
    public Client findClientBySsn(int ssn) {
        return clientRepository.findClientBySsn(ssn)
                .orElseThrow(() -> new EntityNotFoundException("Client with ssn " + ssn + " doesn't exist"));
    }
    
    public Client findClientByEmail(String email) {
        return clientRepository.findClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Client with email " + email + " doesn't exist"));
    }
}
