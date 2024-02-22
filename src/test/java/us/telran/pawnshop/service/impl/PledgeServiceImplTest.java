package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.dto.PledgeCreationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.ItemType;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.entity.enums.PledgeStatus;
import us.telran.pawnshop.repository.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PledgeServiceImplTest {

    @Mock
    private PledgeRepository pledgeRepository;
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PledgeCategoryRepository pledgeCategoryRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private PreciousMetalPriceRepository preciousMetalPriceRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PledgeServiceImpl underTest;

    @Test
    void canAddNewPledge() {
        // Given
        PledgeCreationRequest request = new PledgeCreationRequest();
        request.setItemQuantity(1);
        request.setWeightGross(BigDecimal.valueOf(7));
        request.setWeightNet(BigDecimal.valueOf(6.5));
        request.setItem(ItemType.CHAIN);
        request.setPurity(MetalPurity.GOLD_585);
        request.setDescription("Chain");
        request.setCategoryId(1L);
        request.setClientId(1L);
        request.setProductId(1L);
        request.setManagerId(1L);


        Client client = new Client();
        Product product = new Product();
        PledgeCategory category = new PledgeCategory();
        Manager manager = new Manager();
        PreciousMetalPrice metalPrice = new PreciousMetalPrice();
        metalPrice.setMetalPrice(BigDecimal.valueOf(30));

        given(clientRepository.findById(request.getClientId())).willReturn(Optional.of(client));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
        given(pledgeCategoryRepository.findById(request.getCategoryId())).willReturn(Optional.of(category));
        given(managerRepository.findById(request.getManagerId())).willReturn(Optional.of(manager));
        given(preciousMetalPriceRepository.findByPurity(request.getPurity())).willReturn(Optional.of(metalPrice));

        // When
        underTest.newPledge(request);

        // Then
        ArgumentCaptor<Pledge> argumentCaptor = ArgumentCaptor.forClass(Pledge.class);
        verify(pledgeRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getClient()).isEqualTo(client);
    }

    @Test
    void canGetAllPledges() {
        //When
        underTest.getPledges();

        //Then
        pledgeRepository.findAll();
    }

    @Test
    void canUpdatePledge() {
        //Given
        Long pledgeId = 1L;
        String newDescription = "New description";
        int newItemQuantity = 5;
        PledgeStatus newStatus = PledgeStatus.PLEDGED;
        Pledge existingPledge = new Pledge();
        existingPledge.setStatus(PledgeStatus.PLEDGED);

        given(pledgeRepository.findById(pledgeId)).willReturn(Optional.of(existingPledge));

        //When
        underTest.updatePledge(pledgeId, newDescription, newStatus, newItemQuantity);

        //Then
        assertThat(existingPledge.getDescription()).isEqualTo(newDescription);
        assertThat(existingPledge.getItemQuantity()).isEqualTo(newItemQuantity);
        assertThat(existingPledge.getStatus()).isEqualTo(newStatus);
    }

    @Test
    void itShouldThrowWhenPledgeForUpdateDoesNotExist() {
        //Given
        Long pledgeId = 1L;

        given(pledgeRepository.findById(pledgeId)).willReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> underTest.updatePledge(pledgeId, "description", PledgeStatus.PLEDGED, 1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Pledge with id " + pledgeId + " doesn't exist");
    }

    @Test
    void willThrowWhenStatusTheSame() {
        //Given
        Long pledgeId = 1L;

        PledgeStatus newStatus = PledgeStatus.PLEDGED;
        Pledge existingPledge = new Pledge();
        existingPledge.setStatus(PledgeStatus.COLLECTED);

        given(pledgeRepository.findById(pledgeId)).willReturn(Optional.of(existingPledge));

        //Then
        assertThatThrownBy(() -> underTest.updatePledge(pledgeId, "new description", newStatus, 5))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Pledge in status " + newStatus + ". Up to date");
    }

    @Test
    void canDeletePledgeWhenIdExists() {
        //Given
        Long pledgeId = 1L;

        given(pledgeRepository.existsById(pledgeId)).willReturn(true);

        //When
        underTest.deletePledge(pledgeId);

        //Then
        verify(pledgeRepository, times(1)).deleteById(pledgeId);
    }

    @Test
    void itShouldThrowWhenPledgeForDeleteNotExists() {
        //Given
        Long pledgeId = 1L;

        given(pledgeRepository.existsById(pledgeId)).willReturn(false);

        //Then
        assertThatThrownBy(() -> underTest.deletePledge(pledgeId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Pledge with id " + pledgeId + " doesn't exist");

        verify(pledgeRepository, times(0)).deleteById(pledgeId);
    }

    @Test
    void itShouldReturnPledgeById() {
        //Given
        Long pledgeId = 1L;
        Pledge expectedPledge = new Pledge();
        given(pledgeRepository.findById(pledgeId)).willReturn(Optional.of(expectedPledge));

        //When
        Pledge actualPledge = underTest.findPledgeById(pledgeId);

        //Then
        assertThat(actualPledge).isNotNull().isEqualTo(expectedPledge);
    }

    @Test
    void itShouldThrowWhenPledgeWithIdDoesNotExist() {
        //Given
        Long pledgeId = 1L;

        given(pledgeRepository.findById(pledgeId)).willReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> underTest.findPledgeById(pledgeId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Pledge with id " + pledgeId + " doesn't exist");
    }
}