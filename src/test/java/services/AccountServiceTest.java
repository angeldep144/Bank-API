package services;

import dao.AccountDao;
import models.Account;
import models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountServiceTest {
    AccountDao accountDao = Mockito.mock(AccountDao.class);
    AccountService accountService;

    public AccountServiceTest() { this.accountService = new AccountService(accountDao); }

    @BeforeEach
    void setUp() {
        System.out.println("Beginning test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After test");
    }

    @Test
    void createAccount() {

    }

    @Test
    void getClientAccount() {
        Account testAcc = new Account(1, 1000.00, 1);
        Mockito.when(accountDao.getClientAccount(testAcc.getAccount_id(), testAcc.getAccount_id())).thenReturn(testAcc);

        Account actualResult = accountService.getClientAccount(testAcc.getClient_id(), testAcc.getAccount_id());

        assertEquals(testAcc,actualResult);
    }

    @Test
    void getAllAccounts() {
    }

    @Test
    void accountWithdraw() {
    }

    @Test
    void accountDeposit() {
    }

    @Test
    void deleteAccount() {
    }
}