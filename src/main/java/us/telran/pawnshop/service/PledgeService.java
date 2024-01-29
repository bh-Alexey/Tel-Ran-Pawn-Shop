package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.PledgeStatus;

import java.util.List;

public interface PledgeService {
    void newPledge(Pledge pledge);

    List<Pledge> getPledges();

    void updatePledge(Long pledgeId, String description, PledgeStatus status);

    void deletePledge(Long pledgeId);
}
