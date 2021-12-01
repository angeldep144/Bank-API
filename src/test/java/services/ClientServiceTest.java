package services;

import dao.ClientDao;
import models.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    ClientDao clientDao = Mockito.mock(ClientDao.class);
    ClientService clientService;

    public ClientServiceTest() {
        this.clientService = new ClientService(clientDao);
    }

    @Test
    void createClientValidFirstName() {
        Client client = new Client(1, "John", "Doe");

        Boolean actualResult = clientService.createClient(client);

        assertTrue(actualResult);
    }

    @Test
    void createClientInvalidFirstName() {
        Client client = new Client(1, "John12345678912345678912345678912345678912345678910", "Doe");

        Boolean actualResult = clientService.createClient(client);

        assertFalse(actualResult);
    }

    @Test
    void createClientInvalidLastName() {
        Client client = new Client(1, "Doe", "John12345678912345678912345678912345678912345678910");

        Boolean actualResult = clientService.createClient(client);

        assertFalse(actualResult);
    }

    @Test
    void getClient() {
        Client testClient = new Client(1, "John", "Doe");
        Mockito.when(clientDao.getClient(testClient.getClient_id())).thenReturn(testClient);

        Client actualResult = clientService.getClient(testClient.getClient_id());

        assertEquals(testClient,actualResult);
    }

    @Test
    void getAllClients() {
        // Creating list of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "John", "Doe"));
        clients.add(new Client(2, "Mickey", "Mouse"));
        clients.add(new Client(3, "Donald", "Duck"));
        List<Client> expectedValue = clients;

        Mockito.when(clientDao.getAllClients()).thenReturn(clients);

        List<Client> actualResult = clientService.getAllClients();

        assertEquals(expectedValue,actualResult);
    }

    @Test
    void updateClientNameValid() {
        Client testClient = new Client(1, "John", "Doe");
        Client expectedResult = new Client(testClient.getClient_id(), "Johnathan", "Doeser");

        clientDao.updateClientName(testClient.getClient_id(), "Johnathan", "Doeser");
        Mockito.verify(clientDao, Mockito.times(1)).updateClientName(expectedResult.getClient_id(), expectedResult.getFirst_name(), expectedResult.getLast_name());
    }

    @Test
    void updateClientNameInvalid() {
        Client testClient = new Client(1, "John", "Doe");
        Client expectedResult = new Client(testClient.getClient_id(), "John", "Doe");

        clientDao.updateClientName(testClient.getClient_id(), "John12345678912345678912345678912345678912345678910", "Doeser");
        Mockito.verify(clientDao, Mockito.times(1)).updateClientName(expectedResult.getClient_id(), expectedResult.getFirst_name(), expectedResult.getLast_name());
    }

    @Test
    void deleteClient() {
        Client testClient = new Client(1, "John", "Doe");

        Mockito.when(clientService.deleteClient(testClient.getClient_id())).thenReturn(Boolean.TRUE);

        Boolean actualResult = clientService.deleteClient(testClient.getClient_id());

        assertTrue(actualResult);
    }
}