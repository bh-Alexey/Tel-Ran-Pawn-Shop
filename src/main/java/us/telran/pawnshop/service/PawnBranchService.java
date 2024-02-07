package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.PawnBranch;

import java.util.List;

public interface PawnBranchService {


    void addBranch(String address);

    void updateBranch(Long bankId, String address);

    void deleteBranch(Long bankId);

    List<PawnBranch> getBranches();
}
