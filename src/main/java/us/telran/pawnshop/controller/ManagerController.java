package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public void createNewManager(@RequestBody Manager manager) {
        managerService.addNewManager(manager);
    }

    @DeleteMapping(path = "remove/{managerId}")
    public void deleteManager(@PathVariable("managerId") Long managerId) {
        managerService.deleteManager(managerId);
    }

    @PutMapping(path = "update/{managerId}")
    public void updateManager(
            @PathVariable("managerId") Long managerId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {
        managerService.updateManager(managerId, firstName, lastName, email);
    }
}
