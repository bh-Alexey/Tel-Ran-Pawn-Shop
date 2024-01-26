package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping(path = "api/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    @GetMapping
    public List<Manager> getManagers() {
        return managerService.getAppraisers();
    }

    @PostMapping(value = "new")
    public void createNewClient(@RequestBody Manager manager) {
        managerService.addNewManager(manager);
    }
}
