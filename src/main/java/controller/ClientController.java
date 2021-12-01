package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Client;
import services.ClientService;

import java.util.List;

public class ClientController {

    static ClientService clientService = new ClientService();

    // CREATE ***************************************************
    public static void createClient(Context context) {
        Client client = context.bodyAsClass(Client.class);

        if (clientService.createClient(client)) {
            context.status(201);
            context.result("client created");
        } else {
            context.result("First Name and Last Name entries should not be more than 50 characters respectively");
        }
    }

    // READ ***************************************************

    public static void getAllClients(Context context) throws JsonProcessingException {
        context.contentType("application/json");

        List<Client> clientList = clientService.getAllClients();
        if (clientList.isEmpty()) {
            context.result("No clients registered in database");
        } else {
            context.status(200);
            String jsonString = new ObjectMapper().writeValueAsString(clientList);

            context.result(jsonString);
        }
    }

    public static void getClient(Context context) throws JsonProcessingException {
        context.contentType("application/json");
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));

        Client client = clientService.getClient(client_id);

        if (client == null) {
            context.status(404);
            context.result("No Client with ID: " + client_id + " found.");
        } else {
            context.result(new ObjectMapper().writeValueAsString(client));
        }

    }

    // UPDATE ***************************************************
    // UPDATES NAME PARAMETERS
    public static void updateClientName(Context context) {
        Client client = context.bodyAsClass(Client.class);

        if (clientService.updateClientName(client.getClient_id(), client.getFirst_name(), client.getLast_name())) {
            context.result("Client name updated");
        } else {
            context.status(404);
            context.result("Client was not found");
        }
    }

    // DELETE ***************************************************
    public static void deleteClient(Context context) {
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));

        if (clientService.deleteClient(client_id)) {
            context.status(205);
            context.result("Client with ID " + client_id + " and all of their connected accounts was removed from the DB");
        } else {
            context.status(404);
            context.result("Client with ID: " + client_id + " not found");
        }
    }
}
