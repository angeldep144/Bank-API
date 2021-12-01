package dao;

import models.Client;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    String url;
    String username;
    String password;

    Logger logger = Logger.getLogger(ClientDaoImpl.class);

    public ClientDaoImpl(){
        /*jdbc:postgresql://[your endpoint here]/[the specific database you want to connect to]*/
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/bankapi";
        this.username =  System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public ClientDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // CREATE

    @Override
    public void createClient(Client client) {
        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            //sql that we will be executing
            String sql = "INSERT INTO clients VALUES (DEFAULT, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            //set value of question mark. the parameter index is which question mark you want to assign
            ps.setString(1, client.getFirst_name());
            ps.setString(2, client.getLast_name());

            //execute the update
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    //READ

    @Override
    public Client getClient(Integer client_id) {
        Client client = null;

        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "SELECT * FROM clients WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            //we are setting the value of the question
            ps.setInt(1, client_id);

            //execute the sql statement and return the result set
            ResultSet rs = ps.executeQuery();

            //iterate through the result set
            while(rs.next()) {
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3));
            }

        }catch (SQLException e){
            logger.error(e);
        }

        return client;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "SELECT * FROM clients;";
            PreparedStatement ps = conn.prepareStatement(sql);

            //execute the sql statement and return the result set
            ResultSet rs = ps.executeQuery();

            //iterate through the result set
            while(rs.next()){
                clients.add(new Client(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }

        }catch (SQLException e){
            logger.error(e);
        }

        return clients;
    }

    // UPDATE
    @Override
    public void updateClientName(Integer client_id, String new_fName, String new_lName) {
        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "UPDATE clients SET first_name = ?, last_name = ? WHERE client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            //set value of question mark. the parameter index is which question mark you want to assign
            ps.setString(1, new_fName);
            ps.setString(2, new_lName);
            ps.setInt(3, client_id);

            //execute the update
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    // DELETE
    @Override
    public void deleteClient(Integer client_id) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "DELETE FROM clients WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);

            //execute the update
            ps.executeQuery();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    @Override
    public Boolean checkClient(Integer client_id) {
        Boolean check = Boolean.FALSE;

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM clients WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);

            //execute the update
            check = ps.executeQuery().next();

        }catch (SQLException e){
            logger.error(e);
        }
        return check;
    }
}
