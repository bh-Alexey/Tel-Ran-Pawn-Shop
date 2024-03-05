package us.telran.pawnshop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.repository.ManagerRepository;
import us.telran.pawnshop.security.SecurityUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrentManagerService {

    private final ManagerRepository managerRepository;

    public Manager getCurrentManager() {
        Long currentManagerId = SecurityUtils.getCurrentManagerId();
        Optional<Manager> managerOptional = managerRepository.findById(currentManagerId);
        if (managerOptional.isPresent()) {
            return managerOptional.get();
        }
        throw new EntityNotFoundException("Manager not found");
    }
}
