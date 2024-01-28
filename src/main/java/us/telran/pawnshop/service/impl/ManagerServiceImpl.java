package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.repository.ManagerRepository;
import us.telran.pawnshop.service.ManagerService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    public final ManagerRepository managerRepository;
    @Override
    public List<Manager> getAppraisers() {
        return managerRepository.findAll();
    }

    @Override
    public void addNewManager(Manager manager) {
        Optional<Manager> managerOptional = managerRepository.findManagerByEmail(manager.getEmail());
        if (managerOptional.isPresent()) {
            throw new IllegalStateException("Email registered");
        }
        managerRepository.save(manager);
    }

    @Override
    public void deleteManager(Long managerId) {
        boolean exists = managerRepository.existsById(managerId);
        if (!exists) {
            throw new IllegalStateException("Manager with id " + managerId + " doesn't exist");
        }
        managerRepository.deleteById(managerId);
    }

    @Override
    @Transactional
    public void updateManager(Long managerId,
                             String firstName,
                             String lastName,
                             String email) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalStateException("Manager with id " + managerId + " doesn't exist"));

        if (firstName != null && !firstName.isEmpty() && !Objects.equals(manager.getFirstName(), firstName)) {
            manager.setFirstName(firstName);
        }

        if (lastName != null && !lastName.isEmpty() && !Objects.equals(manager.getLastName(), lastName)) {
            manager.setLastName(lastName);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(manager.getEmail(), email)) {
            Optional<Manager> managerOptional = managerRepository.findManagerByEmail(email);
            if (managerOptional.isPresent()) {
                throw new IllegalStateException("Email registered");
            }
            manager.setEmail(email);
        }
    }
}