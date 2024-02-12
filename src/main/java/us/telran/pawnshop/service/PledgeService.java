package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.PledgeCreationRequest;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.PledgeStatus;

import java.util.List;

public interface PledgeService {

    void newPledge(PledgeCreationRequest pledgeCreationRequest);

    List<Pledge> getPledges();

    void updatePledge(Long pledgeId, String description, PledgeStatus status, int itemQuantity);

    void deletePledge(Long pledgeId);

    Pledge findPledgeById(Long pledgeId);
}
