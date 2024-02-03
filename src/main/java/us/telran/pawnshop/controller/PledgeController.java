package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.PledgeCreationRequest;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.PledgeStatus;
import us.telran.pawnshop.service.PledgeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/pledge")
public class PledgeController {

    private final PledgeService pledgeService;

    @PostMapping(value = "new")
    public void createNewPledge(@RequestBody PledgeCreationRequest pledgeCreationRequest) {
        pledgeService.newPledge(pledgeCreationRequest);
    }

    @GetMapping
    public List<Pledge> getPledges() {
        return pledgeService.getPledges();
    }

    @DeleteMapping(path = "remove/{pledgeId}")
    public void deletePledge(@PathVariable("pledgeId") Long pledgeId) {
        pledgeService.deletePledge(pledgeId);
    }

    @PutMapping(path = "update/{pledgeId}")
    public void updatePledge(
            @PathVariable("pledgeId") Long pledgeId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) PledgeStatus status) {
        pledgeService.updatePledge(pledgeId, description, status);
    }
}
