package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.Pledge;

import java.util.List;

public interface PledgeService {
    void newPledge(Pledge pledge);

    List<Pledge> getPledges();
}
