package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.PledgeCreationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.PledgeStatus;
import us.telran.pawnshop.repository.*;
import us.telran.pawnshop.security.SecurityUtils;
import us.telran.pawnshop.service.PledgeService;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PledgeServiceImpl implements PledgeService {

    private final PledgeRepository pledgeRepository;
    private final ClientRepository clientRepository;
    private final PledgeCategoryRepository pledgeCategoryRepository;
    private final PreciousMetalPriceRepository preciousMetalPriceRepository;
    private final ProductRepository productRepository;

    Long currentManagerId = SecurityUtils.getCurrentManagerId();

    @Override
    @Transactional
    public void newPledge(PledgeCreationRequest pledgeCreationRequest) {
        Client client = clientRepository.findById(pledgeCreationRequest.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Product product = productRepository.findById(pledgeCreationRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + pledgeCreationRequest.getProductId() + " not found"));

        PledgeCategory category = pledgeCategoryRepository.findById(pledgeCreationRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not identified"));

        PreciousMetalPrice metalPrice = preciousMetalPriceRepository.findByPurity(pledgeCreationRequest.getPurity())
                .orElseThrow(() -> new EntityNotFoundException("Price not found"));

        Manager manager = new Manager();
        manager.setManagerId(currentManagerId);

        Pledge pledge = new Pledge();
        pledge.setClient(client);
        pledge.setProduct(product);
        pledge.setCategory(category);
        pledge.setManager(manager);
        pledge.setItem(pledgeCreationRequest.getItem());
        pledge.setItemQuantity(pledgeCreationRequest.getItemQuantity());
        pledge.setDescription(pledgeCreationRequest.getDescription());
        pledge.setPurity(pledgeCreationRequest.getPurity());
        pledge.setWeightGross(pledgeCreationRequest.getWeightGross());
        pledge.setWeightNet(pledgeCreationRequest.getWeightNet());
        pledge.setEstimatedPrice(pledge.getWeightNet().multiply(metalPrice.getMetalPrice()));
        pledge.setStatus(PledgeStatus.PENDING);

        pledgeRepository.save(pledge);
    }

    @Override
    public List<Pledge> getPledges() {
        return pledgeRepository.findAll();
    }

    @Override
    public List<Pledge> getAllByStatus(PledgeStatus status) {
        return pledgeRepository.findAllByStatus(status);
    }

    @Override
    @Transactional
    public void updatePledge(Long pledgeId, String description, PledgeStatus status, int itemQuantity) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new EntityNotFoundException("Pledge with id " + pledgeId + " doesn't exist"));

        pledge.setDescription(description);
        pledge.setItemQuantity(itemQuantity);

        if (!Objects.equals(pledge.getStatus(), status)) {
            throw new IllegalStateException("Pledge in status " + status + ". Up to date");
        }
        pledge.setStatus(status);
    }

    @Override
    @Transactional
    public void deletePledge(Long pledgeId){
        boolean exists = pledgeRepository.existsById(pledgeId);
        if (!exists) {
            throw new EntityNotFoundException("Pledge with id " + pledgeId + " doesn't exist");
        }
        pledgeRepository.deleteById(pledgeId);
    }

    @Override
    public Pledge findPledgeById(Long pledgeId) {
        return pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new EntityNotFoundException("Pledge with id " + pledgeId + " doesn't exist"));
    }
}

