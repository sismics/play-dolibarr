package helpers.api.dolibarr.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import helpers.api.dolibarr.DolibarrClient;
import helpers.api.dolibarr.model.BankAccount;
import helpers.api.dolibarr.model.BankAccountLine;
import okhttp3.Request;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jtremeaux
 */
public class BankAccountsService {
    public DolibarrClient dolibarrClient;

    public BankAccountsService(DolibarrClient dolibarrClient) {
        this.dolibarrClient = dolibarrClient;
    }

    /**
     * Get the list of bank accounts.
     *
     * @return The list of bank accounts
     */
    public List<BankAccount> listBankAccount() {
        Request request = dolibarrClient.authRequest(new Request.Builder()
                .url(dolibarrClient.getUrl("/bankaccounts"))
                .get()
                .build());
        return dolibarrClient.execute(request,
                (response) -> {
                    Type listType = new TypeToken<ArrayList<BankAccount>>(){}.getType();
                    return new Gson().fromJson(response.body().string(), listType);
                },
                (response) -> {
                    throw new RuntimeException("Error getting bank account list, response was: " + response.body().string());
                });
    }

    /**
     * Get the details of a bank account.
     *
     * @param id The bank account id
     * @return The details of the bank account
     */
    public BankAccount getBankAccount(Integer id) {
        Request request = dolibarrClient.authRequest(new Request.Builder()
                .url(dolibarrClient.getUrl("/bankaccounts/" + id))
                .get()
                .build());
        return dolibarrClient.execute(request,
                (response) -> new Gson().fromJson(response.body().string(), BankAccount.class),
                (response) -> {
                    throw new RuntimeException("Error getting bank account detail, response was: " + response.body().string());
                });
    }

    /**
     * Get the lines of a bank account.
     * TODO There is no pagination right now in the Dolibarr API. There is a paramater "limit" for other resources (eg. thirdparties) but not this one.
     *
     * @param id The bank account id
     * @return The lines of the bank account
     */
    public List<BankAccountLine> listBankAccountLines(Integer id) {
        Request request = dolibarrClient.authRequest(new Request.Builder()
                .url(dolibarrClient.getUrl("/bankaccounts/" + id + "/lines"))
                .get()
                .build());
        return dolibarrClient.execute(request,
                (response) -> {
                    Type listType = new TypeToken<ArrayList<BankAccountLine>>(){}.getType();
                    return new Gson().fromJson(response.body().string(), listType);
                },
                (response) -> {
                    throw new RuntimeException("Error getting bank account lines, response was: " + response.body().string());
                });
    }
}
