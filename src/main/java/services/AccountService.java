package services;

import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.ClientDao;
import models.Account;
import java.util.List;

public class AccountService {
    AccountDao accountDao;
    ClientDao clientDao;

    public AccountService() {
        this.accountDao = new AccountDaoImpl();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    // CREATE
    public void createAccount(Integer client_id) { accountDao.createAccount(client_id); }
    // READ
    public Account getClientAccount(Integer client_id, Integer account_id) { return accountDao.getClientAccount(client_id, account_id); }
    public List<Account> getAllAccounts() { return accountDao.getAllAccounts(); }

    // GETS ALL ACCOUNTS REGISTERED UNDER UNIQUE CLIENT ID
    public List<Account> getAllClientAccounts(Integer client_id) { return accountDao.getAllClientAccounts(client_id); }

    public List<Account> filterAccountsByBalance(Integer client_id, Double max, Double min) { return accountDao.filterAccountsByBalance(client_id, max, min); }

    // UPDATE
    public Boolean updateAccount(Integer client_id, Integer account_id, Double account_balance) {
        if (accountDao.checkAccount(client_id, account_id)) {
            accountDao.updateAccount(account_id, account_balance);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // REQUIRES CLIENT ID, ACCOUNT ID, AMOUNT TO WITHDRAW
    public Boolean accountWithdraw(Integer client_id, Integer account_id, Double amount) {
        if (accountDao.getClientAccount(client_id, account_id).getAccount_balance() > amount) {
            accountDao.accountWithdraw(client_id, account_id, amount);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    // REQUIRES CLIENT ID, ACCOUNT ID, AMOUNT TO WITHDRAW
    public void accountDeposit(Integer client_id, Integer account_id, Double amount) {
        accountDao.accountDeposit(client_id, account_id, amount);
    }

    // TRANSFER MONEY
    public Boolean accountTransfer(Integer client_id, Integer accountOne_id, Integer accountTwo_id, Double amount) {
        if (accountDao.getClientAccount(client_id, accountOne_id).getAccount_balance() > amount) {
            accountDao.accountTransfer(accountOne_id, accountTwo_id, amount);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // DELETE
    // REQUIRES UNIQUE ACCOUNT ID
    public void deleteAccount(Integer client_id, Integer account_id) { accountDao.deleteAccount(client_id, account_id); }

    public Boolean checkAccount(Integer client_id, Integer account_id) { return accountDao.checkAccount(client_id, account_id); }
}
