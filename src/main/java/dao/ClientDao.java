package dao;

import models.Client;

import java.util.List;

public interface ClientDao {

    // CREATE
    void createClient(Client client);

    // READ
    Client getClient(Integer client_id);
    List<Client> getAllClients();

    // UPDATE
    void updateClientName(Integer client_id, String new_fName, String new_lName);

    // DELETE
    void deleteClient(Integer client_id);

    Boolean checkClient(Integer client_id);
}
