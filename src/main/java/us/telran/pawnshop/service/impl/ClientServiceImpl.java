package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.enums.ClientStatus;
import us.telran.pawnshop.repository.ClientRepository;
import us.telran.pawnshop.service.ClientService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    public final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public void addNewClient(Client client) {
        Optional<Client> studentOptional = clientRepository.findClientByEmail(client.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        clientRepository.save(client);
    }

    public void deleteClient(Long studentId) {
        boolean exists = clientRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " doesn't exist");
        }
        clientRepository.deleteById(studentId);
    }

    @Transactional
    public void updateClient(Long clientId,
                              String firstname,
                              String lastName,
                              String email) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalStateException("client with id " + clientId + " doesn't exist"));
        if (firstname != null && !firstname.isEmpty() && !Objects.equals(client.getFirstName(), firstname)) {
            client.setFirstName(firstname);
        }

        if (lastName != null && !lastName.isEmpty() && !Objects.equals(client.getLastName(), lastName)) {
            client.setLastName(lastName);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(client.getEmail(), email)) {
            Optional<Client> studentOptional = clientRepository.findClientByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            client.setEmail(email);
        }
    }
}
