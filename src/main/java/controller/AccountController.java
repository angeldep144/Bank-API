package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ClientDao;
import io.javalin.http.Context;
import models.Account;
import models.Payout;
import services.AccountService;
import services.ClientService;

import java.util.List;
import java.util.Objects;

public class AccountController {
    static AccountService accountService = new AccountService();
    static ClientService clientService = new ClientService();

    public static void getAllClientAccounts(Context context) throws JsonProcessingException {
        context.contentType("application/json");
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));

        List<Account> accountList = accountService.getAllClientAccounts(client_id);

        if (!(accountList.isEmpty())) {
            String jsonString = new ObjectMapper().writeValueAsString(accountList);
            context.result(jsonString);
        } else {
            context.status(404);
            context.result("No Client with CLIENT ID: " + client_id + " exists.");
        }
    }

    public static void getClientAccount(Context context) throws JsonProcessingException {
        context.contentType("application/json");
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));

        Account account = accountService.getClientAccount(client_id, account_id);

        if (accountService.checkAccount(client_id, account_id)) {
            context.result(new ObjectMapper().writeValueAsString(account));
        } else {
            context.status(404);
            context.result("No Account with ID: " + account_id + " found under client ID: " + client_id);
        }
    }

    public static void filterAccountsByBalance(Context context) throws JsonProcessingException {
        context.contentType("application/json");
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Double max = Double.parseDouble(context.queryParam("amountLessThan"));
        Double min = Double.parseDouble(context.queryParam("amountGreaterThan"));

        List<Account> accountList = accountService.filterAccountsByBalance(client_id, max, min);

        if (accountList.isEmpty()) {
            context.status(404);
            context.result("No accounts found after filtering.");
        } else {
            String jsonString = new ObjectMapper().writeValueAsString(accountList);
            context.result(jsonString);
        }
    }

    public static void createAccount(Context context) {
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        if (clientService.checkClient(client_id)) {
            accountService.createAccount(client_id);
            context.status(201);
            context.result("Account for Client with ID: " + client_id + " was created");
        } else {
            context.status(404);
            context.result("Client with CLIENT ID: " + client_id + " does not exist");
        }
    }

    public static void updateAccount(Context context) {
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));

        Account acc = context.bodyAsClass(Account.class);

        if (accountService.checkAccount(client_id, account_id)) {
            accountService.updateAccount(client_id, account_id, acc.getAccount_balance());
            context.result("Account with ID: " + account_id + " has successfully been updated.");
        } else {
            context.status(404);
            context.result("No such");
        }
    }

    public static void updateAccountBalance(Context context) {
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));

        Payout amount = context.bodyAsClass(Payout.class);

        if (clientService.checkClient(client_id) & (amount.getWithdraw() != null)) {
            if (accountService.accountWithdraw(client_id, account_id, amount.getWithdraw())){
                context.result("You have successfully withdrawn " + -(amount.getWithdraw()) + " from ACCOUNT ID=" + account_id + " for CLIENT ID: " + client_id);
            } else {
                context.status(422);
                context.result("You do not have enough funds in your account to complete this transaction");
            }

        } else if (clientService.checkClient(client_id) & (amount.getDeposit() != null)) {
            accountService.accountDeposit(client_id, account_id, amount.getDeposit());
            context.result("You have successfully deposited " + amount.getDeposit() + " into ACCOUNT ID=" + account_id + " for CLIENT ID: " + client_id);

        } else {
            context.status(404);
            context.result("Client with CLIENT ID: " + client_id + " does not exist");
        }

    }

    public static void accountTransfer(Context context) {
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer accountOne_id = Integer.parseInt(context.pathParam("account_id"));
        Integer accountTwo_id = Integer.parseInt(context.pathParam("account2"));

        Payout amount = context.bodyAsClass(Payout.class);

        if (accountService.accountTransfer(client_id, accountOne_id, accountTwo_id, amount.getTransfer())) {
            context.result("You have successfully transferred $" + amount.getTransfer() + " from ACCOUNT ID: " + accountOne_id + " to ACCOUNT ID: " + accountTwo_id);
        } else {
            if (clientService.checkClient(client_id)) {
                context.status(422);
                context.result("Insufficient funds");
            } else {
                context.status(404);
                context.result("No Account (or) Client found");
            }
        }
    }

    public static void deleteAccount(Context context) {
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));

        accountService.deleteAccount(client_id, account_id);

        context.result("Deleted account with id " + account_id + " if it exists");
    }

}
