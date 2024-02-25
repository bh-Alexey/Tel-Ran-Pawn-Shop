package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.service.PawnBranchService;

import java.util.List;

@RestController
@RequestMapping(path = "pawn-shop/branches")
@RequiredArgsConstructor
public class PawnBranchController {

    private final PawnBranchService pawnBranchService;

    @PostMapping(value = "new")
    @Operation(summary = "NEW BRANCH", description = "Add new pawn shop branch to the DB")
    public void addBranch(@RequestParam String address) {
        pawnBranchService.addBranch(address);
    }

    @GetMapping(value = "show")
    @Operation(summary = "ALL BRANCHES", description = "Show all pawn shop branches in DB")
    public List<PawnBranch> getAllBranches() {
        return pawnBranchService.getBranches();
    }

    @PutMapping(path = "update/{branchId}")
    @Operation(summary = "EDIT BRANCH", description = "Change details of branch with specified id")
    public void updateBranch(
            @PathVariable("branchId") Long branchId,
            @RequestParam(required = false) String address) {
        pawnBranchService.updateBranch(branchId, address);
    }

    @DeleteMapping(path = "remove/{branchId}")
    @Operation(summary = "DELETE BRANCH", description = "Delete branch with specified id from the DB")
    public void deleteBranch(@PathVariable("branchId") Long branchId) {
        pawnBranchService.deleteBranch(branchId);
    }
}
