package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.dto.ManagerCreationRequest;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.repository.ManagerRepository;
import us.telran.pawnshop.service.ManagerService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Manager> getManagers() {
        return managerRepository.findAll();
    }

    @Override
    @Transactional
    public void addNewManager(ManagerCreationRequest managerCreationRequest) {

        Optional<Manager> managerOptional = managerRepository.findManagerByEmail(managerCreationRequest.getEmail());
        if (managerOptional.isPresent()) {
            throw new EntityExistsException("Email registered");
        }

        Manager manager = new Manager();
        manager.setFirstName(managerCreationRequest.getFirstName());
        manager.setLastName(managerCreationRequest.getLastName());
        manager.setEmail(managerCreationRequest.getEmail());
        manager.setPassword(passwordEncoder.encode(managerCreationRequest.getPassword()));
        manager.setManagerStatus(managerCreationRequest.getManagerStatus());

        managerRepository.save(manager);
    }

    @Override
    @Transactional
    public void deleteManager(Long managerId) {

        boolean exists = managerRepository.existsById(managerId);
        if (!exists) {
            throw new EntityNotFoundException("Manager with id " + managerId + " doesn't exist");
        }
        managerRepository.deleteById(managerId);
    }

    @Override
    @Transactional
    public void updateManager(Long managerId,
                             String firstName,
                             String lastName,
                             String email,
                             String password
    ) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Manager with id " + managerId + " doesn't exist"));

        if (firstName != null
                   && !firstName.isEmpty()
                   && !Objects.equals(manager.getFirstName(), firstName)
        ) {
            manager.setFirstName(firstName);
        }

        if (lastName != null
                  && !lastName.isEmpty()
                  && !Objects.equals(manager.getLastName(), lastName)
        ) {
            manager.setLastName(lastName);
        }

        if (email != null
               && !email.isEmpty()
               && !Objects.equals(manager.getEmail(), email)
        ) {
            Optional<Manager> managerOptional = managerRepository.findManagerByEmail(email);
            if (managerOptional.isPresent()) {
                throw new EntityExistsException("Email registered");
            }
            manager.setEmail(email);
            manager.setPassword(passwordEncoder.encode(password));
        }
    }
}
