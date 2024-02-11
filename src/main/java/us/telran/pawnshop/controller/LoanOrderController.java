package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.service.PawnBranchService;

import java.util.List;

@RestController
@RequestMapping(path ="api/loan-orders")
@RequiredArgsConstructor
public class LoanOrderController {

    private final PawnBranchService pawnBranchService;

    @PostMapping(value = "new")
    public void addBranch(@RequestParam String address) {
        pawnBranchService.addBranch(address);
    }
    @GetMapping
    public List<PawnBranch> getAllBranches() {
        return pawnBranchService.getBranches();
    }
    @PutMapping(path = "update/{branchId}")
    public void updateBranch(
            @PathVariable("branchId") Long branchId,
            @RequestParam(required = false) String address) {
        pawnBranchService.updateBranch(branchId, address);
    }

    @DeleteMapping(path = "remove/{branchId}")
    public void deleteBranch(@PathVariable("branchId") Long branchId) {
        pawnBranchService.deleteBranch(branchId);
    }
}

