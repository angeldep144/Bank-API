package dao;

import models.Account;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    String url;
    String username;
    String password;

    Logger logger = Logger.getLogger(AccountDaoImpl.class);

    public AccountDaoImpl(){
        /*jdbc:postgresql://[your endpoint here]/[the specific database you want to connect to]*/
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/bankapi";
        this.username =  System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public AccountDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // CREATE
    // REQUIRES CLIENT ID TO CREATE A NEW ACCOUNT
    // AN ACCOUNT CANNOT EXIST WITHOUT A CLIENT TO REFERENCE

    @Override
    public void createAccount(Integer client_id) {
        /*
        * CREATES NEW ACCOUNT FOR PRE-EXISTING CLIENT
        * */
        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "INSERT INTO accounts VALUES (DEFAULT, DEFAULT, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            //set value of question mark. the parameter index is which question mark you want to assign
            ps.setInt(1, client_id);

            //execute the update
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }

    }

    // READ

    // GET ALL ACCOUNTS IN THE ACCOUNTS TABLE
    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "SELECT * FROM accounts;";
            PreparedStatement ps = conn.prepareStatement(sql);

            //execute the sql statement and return the result set
            ResultSet rs = ps.executeQuery();

            //iterate through the result set
            while(rs.next()){
                accounts.add(new Account(rs.getInt(1),rs.getDouble(2),rs.getInt(3)));
            }

        }catch (SQLException e){
            logger.error(e);
        }

        return accounts;
    }

    // GETS ONE CLIENT ACCOUNT
    // REQUIRES CLIENT ID AND ACCOUNT ID
    @Override
    public Account getClientAccount(Integer client_id, Integer account_id) {
        Account account = null;

        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "SELECT * FROM accounts WHERE account_id = ? AND client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            //we are setting the value of the question
            ps.setInt(1, account_id);
            ps.setInt(2, client_id);

            //execute the sql statement and return the result set
            ResultSet rs = ps.executeQuery();

            //iterate through the result set
            while(rs.next()) {
                account = new Account(rs.getInt(1), rs.getDouble(2), rs.getInt(3));
            }

        }catch (SQLException e){
            logger.error(e);
        }

        return account;
    }

    // GETS ALL ACCOUNTS REGISTERED UNDER ONE CLIENT
    // REQUIRES CLIENT ID
    @Override
    public List<Account> getAllClientAccounts(Integer client_id) {
        List<Account> client_accounts = new ArrayList<>();

        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "SELECT * FROM accounts WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);

            //execute the sql statement and return the result set
            ResultSet rs = ps.executeQuery();

            //iterate through the result set
            while(rs.next()){
                client_accounts.add(new Account(rs.getInt(1),rs.getDouble(2),rs.getInt(3)));
            }

        }catch (SQLException e){
            logger.error(e);
        }

        return client_accounts;
    }

    public List<Account> filterAccountsByBalance(Integer client_id, Double max, Double min) {
        List<Account> client_accounts = new ArrayList<>();

        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND account_balance BETWEEN ? AND ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);
            ps.setDouble(2, min);
            ps.setDouble(3, max);

            //execute the sql statement and return the result set
            ResultSet rs = ps.executeQuery();

            //iterate through the result set
            while(rs.next()){
                client_accounts.add(new Account(rs.getInt(1),rs.getDouble(2),rs.getInt(3)));
            }

        }catch (SQLException e){
            logger.error(e);
        }

        return client_accounts;
    }

    // UPDATE
    @Override
    public void updateAccount(Integer account_id, Double account_balance) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources
            //sql that we will be executing
            String sql = "UPDATE accounts SET account_balance = ? WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            //set value of question mark. the parameter index is which question mark you want to assign
            ps.setDouble(1, account_balance);
            ps.setInt(2, account_id);

            //execute the update
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    // WITHDRAW FROM ACCOUNT (CURRENT_BALANCE - WITHDRAW_AMOUNT = NEW BALANCE)
    // REQUIRES (CLIENT ID), (ACCOUNT ID), (AMOUNT TO WITHDRAW)
    @Override
    public void accountWithdraw(Integer client_id, Integer account_id, Double amount) {
        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "UPDATE accounts SET account_balance = (account_balance - ?) WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            //set value of question mark. the parameter index is which question mark you want to assign
            ps.setDouble(1, amount);
            ps.setInt(2, client_id);
            ps.setInt(3, account_id);

            //execute the update
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    public void accountDeposit(Integer client_id, Integer account_id, Double amount) {
        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "UPDATE accounts SET account_balance = (account_balance + ?) WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            //set value of question mark. the parameter index is which question mark you want to assign
            ps.setDouble(1, amount);
            ps.setInt(2, client_id);
            ps.setInt(3, account_id);

            //execute the update
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    // TRANSFER
    @Override
    public void accountTransfer(Integer accountOne_id, Integer accountTwo_id, Double amount) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "UPDATE accounts SET account_balance = (account_balance - ?) WHERE account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setInt(2, accountOne_id);
            ps.executeUpdate();

            sql = "UPDATE accounts SET account_balance = (account_balance + ?) WHERE account_id = ?;";
            ps = conn.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setInt(2, accountTwo_id);
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    // DELETE
    //
    @Override
    public void deleteAccount(Integer client_id, Integer account_id) {
        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            //sql that we will be executing
            String sql = "DELETE FROM accounts WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            //set value of question mark. the parameter index is which question mark you want to assign
            ps.setInt(1, client_id);
            ps.setInt(2, account_id);

            //execute the update
            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    @Override
    public Boolean checkAccount(Integer client_id, Integer account_id) {
        Boolean check = Boolean.FALSE;

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM accounts WHERE account_id = ? AND client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, account_id);
            ps.setInt(2, client_id);
            check = ps.executeQuery().next();

        }catch (SQLException e){
            logger.error(e);
        }
        return check;
    }
}
