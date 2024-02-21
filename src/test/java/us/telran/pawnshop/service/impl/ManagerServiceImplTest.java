package us.telran.pawnshop.service.impl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.dto.ManagerCreationRequest;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.repository.ManagerRepository;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerServiceImpl underTest;

    @Test
    void canGetAllManagers() {
        //When
        underTest.getManagers();

        //Then
        verify(managerRepository).findAll();
    }

    @Test
    void canAddNewManager() {
        //Given
        String email = "email@email.com";
        ManagerCreationRequest request = new ManagerCreationRequest();
        request.setEmail(email);

        //When
        when(managerRepository.findManagerByEmail(email)).thenReturn(Optional.empty());

        underTest.addNewManager(request);

        //Then
        verify(managerRepository, times(1)).save(any(Manager.class));
    }

    @Test
    void willThrowIfManagerExist() {
        //Given
        ManagerCreationRequest request = new ManagerCreationRequest();
        Manager existingManager = new Manager();

        //When
        when(managerRepository.findManagerByEmail(request.getEmail())).thenReturn(Optional.of(existingManager));

        //Then
        assertThatThrownBy(() -> underTest.addNewManager(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email registered");
    }

    @Test
    void canDeleteManager() {
        //Given
        Long managerId = 1L;

        //When
        when(managerRepository.existsById(managerId)).thenReturn(true);

        underTest.deleteManager(managerId);

        //Then
        verify(managerRepository).deleteById(managerId);
    }

    @Test
    void willThrowWhenManagerForDeleteDoesNotExist() {
        //Given
        Long managerId = 1L;

        //When
        when(managerRepository.existsById(managerId)).thenReturn(false);

        //Then
        assertThatThrownBy(() -> underTest.deleteManager(managerId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageMatching("Manager with id " + managerId + " doesn't exist");
    }

    @Test
    void canUpdateManager() {
        Long managerId = 1L;
        String updatedFirstName = "Merry";
        String updatedLastName = "Poppins";
        String updatedEmail = "merry.poppins@email.com";
        Manager existingManager = new Manager();

        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(existingManager));
        when(managerRepository.findManagerByEmail(anyString())).thenReturn(Optional.empty());

        underTest.updateManager(managerId, updatedFirstName, updatedLastName, updatedEmail);

        verify(managerRepository, times(1)).findById(managerId);
        verify(managerRepository, times(1)).findManagerByEmail(updatedEmail);
        assertThat(existingManager.getFirstName()).isEqualTo(updatedFirstName);
        assertThat(existingManager.getLastName()).isEqualTo(updatedLastName);
        assertThat(existingManager.getEmail()).isEqualTo(updatedEmail);
    }

    @Test
    void willThrowWhenManageForUpdateDoesNotExist() {
        //Given
        Long managerId = 1L;

        //When
        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> underTest.updateManager(managerId, "name",
                "lastName", "email@email.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageMatching("Manager with id " + managerId + " doesn't exist");
    }

    @Test
    void willThrowWhenTryToAddExistingEmail() {
        //Given
        Long managerId = 1L;
        String updatedFirstName = "John";
        String updatedLastName = "Doe";
        String updatedEmail = "john.doe@example.com";
        String email = "email@email.com";

        Manager existingManager = new Manager();
        existingManager.setEmail(updatedEmail);

        Manager anotherManager = new Manager();
        anotherManager.setEmail(updatedEmail);


        //When
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(existingManager));

        underTest.updateManager(managerId, updatedFirstName, updatedLastName, updatedEmail);

        when(managerRepository.findManagerByEmail(email)).thenReturn(Optional.of(anotherManager));

        //Then
        assertThatThrownBy(() -> underTest.updateManager(managerId, "name",
                "lastName", "email@email.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email registered");

        verify(managerRepository, never()).save(any());
    }
}