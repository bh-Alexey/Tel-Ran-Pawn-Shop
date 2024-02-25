package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.ManagerCreationRequest;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping(path = "pawn-shop/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping(value = "new")
    @Operation(summary = "ADD NEW MANAGER", description = "Create and save manager to DB")
    public void createNewManager(@RequestBody ManagerCreationRequest managerCreationRequest) {
        managerService.addNewManager(managerCreationRequest);
    }

    @GetMapping(value = "show")
    @Operation(summary = "ALL MANAGERS", description = "Show all managers")
    public List<Manager> getManagers() {
        return managerService.getManagers();
    }

    @PutMapping(path = "update/{managerId}")
    @Operation(summary = "EDIT MANAGER", description = "Change manager's personal information." +
            " Manager with specified id can be modified and saved to the DB")
    public void updateManager(
            @PathVariable("managerId") Long managerId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        managerService.updateManager(managerId, firstName, lastName, email, password);
    }

    @DeleteMapping(path = "remove/{managerId}")
    @Operation(summary = "DELETE MANAGER", description = "Remove manager with specified id from DB")
    public void deleteManager(@PathVariable("managerId") Long managerId) {
        managerService.deleteManager(managerId);
    }

}
