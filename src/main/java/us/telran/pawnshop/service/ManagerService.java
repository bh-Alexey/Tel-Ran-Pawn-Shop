package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.ManagerCreationRequest;
import us.telran.pawnshop.entity.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getManagers();

    void addNewManager(ManagerCreationRequest managerCreationRequest);

    void deleteManager(Long managerId);

    void updateManager(Long managerId, String firstName, String lastName, String email);
}
