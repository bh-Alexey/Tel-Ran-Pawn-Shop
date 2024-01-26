package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getAppraisers();

    void addNewManager(Manager manager);

    void deleteManager(Long managerId);

    void updateManager(Long managerId, String firstName, String lastName, String email);
}
