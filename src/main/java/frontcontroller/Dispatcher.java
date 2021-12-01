package frontcontroller;

import controller.AccountController;
import controller.ClientController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Dispatcher {
    public Dispatcher(Javalin app) {
        app.routes(() -> {
            path("clients", () -> {
                get(ClientController::getAllClients);
                post(ClientController::createClient);

                path("{client_id}", () -> {
                    get(ClientController::getClient);
                    put(ClientController::updateClientName);
                    delete(ClientController::deleteClient);

                    path("accounts", () -> {
                        get(AccountController::getAllClientAccounts);
                        post(AccountController::createAccount);

                        path("filter", () -> {
                            get(AccountController::filterAccountsByBalance);
                        });

                        path("{account_id}", () -> {
                            //get(AccountController::filterAccountsByBalance);
                            get(AccountController::getClientAccount);
                            delete(AccountController::deleteAccount);
                            put(AccountController::updateAccount);
                            patch(AccountController::updateAccountBalance);

                            path("transfer/{account2}", () -> {
                                patch(AccountController::accountTransfer);
                            });
                        });
                    });
                });
            });
        });
    }
}
