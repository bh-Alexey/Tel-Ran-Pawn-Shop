package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.PledgeCreationRequest;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.PledgeStatus;
import us.telran.pawnshop.service.PledgeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "pawn-shop/pledges")
public class PledgeController {

    private final PledgeService pledgeService;

    @PostMapping(value = "new")
    @Operation(summary = "ADD NEW PLEDGE", description = "Create and save pledge to DB. New pledges will have \"Pledged\" status")
    public void createNewPledge(@RequestBody PledgeCreationRequest pledgeCreationRequest) {
        pledgeService.newPledge(pledgeCreationRequest);
    }

    @GetMapping(value = "all")
    @Operation(summary = "ALL PLEDGES", description = "Show pledges for all time")
    public List<Pledge> getPledges() {
        return pledgeService.getPledges();
    }

    @PutMapping(path = "update/{pledgeId}")
    @Operation(summary = "EDIT PLEDGE", description = "Edit information of pledge with specified id." +
            " Modify and save to the DB")
    public void updatePledge(
            @PathVariable("pledgeId") Long pledgeId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) PledgeStatus status,
            @RequestParam(required = false) int itemQuantity) {
        pledgeService.updatePledge(pledgeId, description, status, itemQuantity);
    }

    @DeleteMapping(path = "remove/{pledgeId}")
    @Operation(summary = "DELETE PLEDGE", description = "Remove pledge with specified id from the DB")
    public void deletePledge(@PathVariable("pledgeId") Long pledgeId) {
        pledgeService.deletePledge(pledgeId);
    }

}
