package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.dto.ClientCreationRequest;
import us.telran.pawnshop.dto.ClientRealCreationRequest;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.repository.ClientRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl underTest;

    @Test
    void canAddNewClient() {
        //Given
        ClientCreationRequest request = new ClientCreationRequest();
        request.setEmail("test@test.com");

        //When
        when(clientRepository.findClientByEmail(request.getEmail())).thenReturn(Optional.empty());

        underTest.addNewClient(request);

        //Then
        verify(clientRepository, times(1)).findClientByEmail(request.getEmail());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void itShouldThrowExceptionWhenClientExists() {
        //Given
        Long clientId = 1L;
        String email = "email@email.com";
        ClientCreationRequest request = new ClientCreationRequest();
        request.setEmail(email);

        Client client = new Client();
        client.setClientId(clientId);
        client.setEmail(email);

        //When
        when(clientRepository.findClientByEmail(request.getEmail())).thenReturn(Optional.of(client));

        //Then
        String expectedExceptionMessage = String.format("Email registered for client with id %d", client.getClientId());
        assertThatThrownBy(() -> underTest.addNewClient(request))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining(expectedExceptionMessage);
        verify(clientRepository, times(1)).findClientByEmail(request.getEmail());
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    public void canAddNewRealClient() {
        // Given
        int socialSecurityNumber = 123456789;
        ClientRealCreationRequest request = new ClientRealCreationRequest();
        request.setSocialSecurityNumber(socialSecurityNumber);

        //When
        when(clientRepository.findClientBySsn(socialSecurityNumber)).thenReturn(Optional.empty());

        underTest.addNewRealClient(request);

        // Then
        verify(clientRepository, times(1)).save(any());
    }

    @Test
    void itShouldThrowWhenRealClientExists() {
        // Given
        Client existingClient = new Client();
        int socialSecurityNumber = 123456789;
        existingClient.setSocialSecurityNumber(socialSecurityNumber);

        ClientRealCreationRequest request = new ClientRealCreationRequest();
        request.setSocialSecurityNumber(socialSecurityNumber);

        //When
        when(clientRepository.findClientBySsn(socialSecurityNumber))
                .thenReturn(Optional.of(existingClient));

        //Then
        assertThatThrownBy(() -> underTest.addNewRealClient(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Client already registered with id " + existingClient.getClientId());
    }

    @Test
    void canGetAllClients() {
        //When
        underTest.getClients();

        //Then
        verify(clientRepository).findAll();
    }

    @Test
    void canDeleteClient() {
        //Given
        Long clientId = 1L;
        Client client = new Client();

        //When
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        underTest.deleteClient(clientId);

        //Then
        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    void itShouldThrowWhenClientForDeleteDoesNotExists() {
        //Given
        Long clientId = 1L;
        String expectedMessage = "Client with id " + clientId + " doesn't exist";
        given(clientRepository.findById(clientId)).willReturn(Optional.empty());

        // When // Then
        assertThatThrownBy(() -> underTest.deleteClient(clientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    void canUpdateClient() {
        //Given
        Long clientId = 11L;
        String updatedEmail = "danny.devito@domain.com";
        LocalDate updatedDateOfBirth = LocalDate.now().minusYears(18);

        Client client = new Client();
        client.setFirstName("FirstName");
        client.setLastName("LastName");
        client.setDateOfBirth(LocalDate.of(1990, 1, 1));
        client.setEmail("email@email.com");
        client.setAddress("Address");

        when(clientRepository.findClientByEmail(updatedEmail)).thenReturn(Optional.empty());
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        // Act
        underTest.updateClient(clientId, "Danny", "De Vito", updatedDateOfBirth,
                updatedEmail, "New Address");

        // Assert
        assertThat(client.getFirstName()).isEqualTo("Danny");
        assertThat(client.getLastName()).isEqualTo("De Vito");
        assertThat(client.getDateOfBirth()).isEqualTo(updatedDateOfBirth);
        assertThat(client.getEmail()).isEqualTo(updatedEmail);
        assertThat(client.getAddress()).isEqualTo("New Address");
    }

    @Test
    void itShouldThrowWhenClientEmailAlreadyExists() {
        //Given
        Long clientId = 1L;
        String existingEmail = "existingEmail@domain.com";
        Client existingClient = new Client();

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));
        when(clientRepository.findClientByEmail(existingEmail)).thenReturn(Optional.of(existingClient));

        // Act and Assert
        assertThatThrownBy(() -> {
            underTest.updateClient(clientId, "New_FirstName", "New_LastName", LocalDate.now().minusYears(19),
                    existingEmail, "New_Address");
        }).isInstanceOf(IllegalStateException.class).hasMessageContaining("Email registered");
    }

    @Test
    void canGetClientById() {
        //Given
        Long clientId = 1L;
        Client expectedClient = new Client();

        //When
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));

        Client actualClient = underTest.getClientById(clientId);

        //Then
        assertThat(actualClient).isEqualTo(expectedClient);
    }

    @Test
    public void itShouldThrowIfClientWithIdNotFound() {
        //Given
        Long clientId = 1L;

        //When
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(
                () -> underTest.getClientById(clientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client with id " + clientId + " doesn't exist");
    }

    @Test
    void canFindClientBySsn() {
        //Given
        int clientSsn = 123456789;
        Client expectedClient = new Client();

        //When
        when(clientRepository.findClientBySsn(clientSsn)).thenReturn(Optional.of(expectedClient));

        Client actualClient = underTest.findClientBySsn(clientSsn);

        //Then
        assertThat(actualClient).isEqualTo(expectedClient);
    }

    @Test

    void itShouldThrowIfClientWithSsnNotFound() {
        //Given
        int clientSsn = 123456789;

        //When
        when(clientRepository.findClientBySsn(clientSsn)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(
                () -> underTest.findClientBySsn(clientSsn))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client with ssn " + clientSsn + " doesn't exist");

    }

    @Test
    void canFindClientByEmail() {
        //Given
        String email = "mail@email.com";
        Client expectedClient = new Client();

        //When
        when(clientRepository.findClientByEmail(email)).thenReturn(Optional.of(expectedClient));

        Client actualClient = underTest.findClientByEmail(email);

        //Then
        assertThat(actualClient).isEqualTo(expectedClient);
    }

    @Test
    void itShouldThrowIfClientWithEmailNotFound() {
        //Given
        String email = "test@test.com";

        //When
        when(clientRepository.findClientByEmail(email)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(
                () -> underTest.findClientByEmail(email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client with email " + email + " doesn't exist"
        );
    }
}