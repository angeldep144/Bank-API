package services;

import dao.ClientDao;
import dao.ClientDaoImpl;
import models.Client;

import java.util.List;

public class ClientService {
    ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public ClientService() {
        this.clientDao = new ClientDaoImpl();
    }

    // CREATE
    // creating a client profile requires the client's first and last names are not over 50 characters
    public Boolean createClient(Client client) {
        if(client.getFirst_name().length() > 50)
            return false;
        if(client.getLast_name().length() > 50)
            return false;

        clientDao.createClient(client);
        return true;
    }

    // READ
    public Client getClient(Integer client_id) { return clientDao.getClient(client_id); }
    public List<Client> getAllClients() { return clientDao.getAllClients(); }

    //UPDATE
    public Boolean updateClientName(Integer client_id, String new_fName, String new_lName) {
        if (clientDao.getClient(client_id) == null) {
            return Boolean.FALSE;
        } else if ((new_fName.length() < 50) & (new_lName.length() < 50)) {
            clientDao.updateClientName(client_id, new_fName, new_lName);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // DELETE
    public Boolean deleteClient(Integer client_id) {
        if (clientDao.checkClient(client_id)) {
            clientDao.deleteClient(client_id);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Boolean checkClient(Integer client_id) {
        return clientDao.checkClient(client_id);
    }
}
