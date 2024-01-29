package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.PledgeStatus;
import us.telran.pawnshop.repository.PledgeRepository;
import us.telran.pawnshop.service.PledgeService;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PledgeServiceImpl implements PledgeService {

    private final PledgeRepository pledgeRepository;

    @Override
    public void newPledge(Pledge pledge) {
        pledgeRepository.save(pledge);
    }

    @Override
    public List<Pledge> getPledges() {
        return pledgeRepository.findAll();
    }

    @Override
    @Transactional
    public void updatePledge(Long pledgeId, String description, PledgeStatus status) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalStateException("Client with id " + pledgeId + " doesn't exist"));

        pledge.setDescription(description);

        if (!Objects.equals(pledge.getStatus(), status)) {
            throw new IllegalStateException("Pledge in status " + status + ". Up to date");
        }
        pledge.setStatus(status);
    }

    @Override
    public void deletePledge(Long pledgeId){
        boolean exists = pledgeRepository.existsById(pledgeId);
        if (!exists) {
            throw new IllegalStateException("Pledge with id " + pledgeId + " doesn't exist");
        }
        pledgeRepository.deleteById(pledgeId);
    }
}

