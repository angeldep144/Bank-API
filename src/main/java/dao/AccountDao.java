package dao;

import models.Account;

import java.util.List;

public interface AccountDao {

    // CREATE
    void createAccount(Integer client_id);

    // READ
    List<Account> getAllAccounts();

    Account getClientAccount(Integer client_id, Integer account_id);
    List<Account> getAllClientAccounts(Integer client_id);

    List<Account> filterAccountsByBalance(Integer client_id, Double max, Double min);

    // UPDATE
    void updateAccount(Integer account_id, Double account_balance);

    void accountWithdraw(Integer client_id, Integer account_id, Double amount);
    void accountDeposit(Integer client_id, Integer account_id, Double amount);

    void accountTransfer(Integer accountOne_id, Integer accountTwo_id, Double amount);

    // DELETE
    void deleteAccount(Integer client_id, Integer account_id);

    Boolean checkAccount(Integer client_id, Integer account_id);
}
