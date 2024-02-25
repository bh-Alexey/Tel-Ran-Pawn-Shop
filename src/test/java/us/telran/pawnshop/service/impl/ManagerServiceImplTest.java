package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import us.telran.pawnshop.dto.ManagerCreationRequest;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.repository.ManagerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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
        request.setPassword("some password");

        //When
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
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
                .isInstanceOf(EntityExistsException.class)
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
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageMatching("Manager with id " + managerId + " doesn't exist");
    }

    @Test
    void canUpdateManager() {
        Long managerId = 1L;
        String updatedFirstName = "Merry";
        String updatedLastName = "Poppins";
        String updatedEmail = "merry.poppins@email.com";
        String updatedPassword = "password";
        Manager existingManager = new Manager();

        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(existingManager));
        when(managerRepository.findManagerByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(updatedPassword)).thenReturn("encodedPassword");
        existingManager.setPassword(passwordEncoder.encode(updatedPassword));

        underTest.updateManager(managerId, updatedFirstName, updatedLastName, updatedEmail, updatedPassword);

        verify(managerRepository, times(1)).findById(managerId);
        verify(managerRepository, times(1)).findManagerByEmail(updatedEmail);
        assertThat(existingManager.getFirstName()).isEqualTo(updatedFirstName);
        assertThat(existingManager.getLastName()).isEqualTo(updatedLastName);
        assertThat(existingManager.getEmail()).isEqualTo(updatedEmail);
        assertThat(existingManager.getPassword()).isEqualTo(passwordEncoder.encode(updatedPassword));
    }

    @Test
    void willThrowWhenManageForUpdateDoesNotExist() {
        //Given
        Long managerId = 1L;

        //When
        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> underTest.updateManager(managerId, "name",
                "lastName", "email@email.com", "password"))
                .isInstanceOf(EntityNotFoundException.class)
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
        String updatedPassword = "password";

        Manager existingManager = new Manager();
        existingManager.setEmail(updatedEmail);

        Manager anotherManager = new Manager();
        anotherManager.setEmail(updatedEmail);


        //When
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(existingManager));

        underTest.updateManager(managerId, updatedFirstName, updatedLastName, updatedEmail, updatedPassword);

        when(managerRepository.findManagerByEmail(email)).thenReturn(Optional.of(anotherManager));

        //Then
        assertThatThrownBy(() -> underTest.updateManager(managerId, "name",
                "lastName", "email@email.com", "password"))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Email registered");

        verify(managerRepository, never()).save(any());
    }
}