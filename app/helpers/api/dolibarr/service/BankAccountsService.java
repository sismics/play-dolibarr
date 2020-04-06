package helpers.api.dolibarr.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sismics.sapparot.string.StringUtil;
import helpers.api.dolibarr.DolibarrClient;
import helpers.api.dolibarr.model.api.DolibarrApiResponse;
import helpers.api.dolibarr.model.bankAccount.BankAccount;
import helpers.api.dolibarr.model.bankAccount.BankAccountLine;
import helpers.api.dolibarr.model.bankAccount.BankAccountsTransferModel;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
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
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/bankaccounts"))))
                .GET()
                .build();
        return dolibarrClient.execute(request,
                (response) -> {
                    Type listType = new TypeToken<ArrayList<BankAccount>>(){}.getType();
                    return new Gson().fromJson(new JsonReader(new InputStreamReader(response.body())), listType);
                },
                (response) -> {
                    throw new RuntimeException("Error getting bank account list, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Get the details of a bank account.
     *
     * @param id The bank account id
     * @return The details of the bank account
     */
    public BankAccount getBankAccount(Integer id) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/bankaccounts/" + id))))
                .GET()
                .build();
        return dolibarrClient.execute(request,
                (response) -> new Gson().fromJson(new JsonReader(new InputStreamReader(response.body())), BankAccount.class),
                (response) -> {
                    throw new RuntimeException("Error getting bank account detail, response was: " + StringUtil.toString(response.body()));
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
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/bankaccounts/" + id + "/lines"))))
                .GET()
                .build();
        return dolibarrClient.execute(request,
                (response) -> {
                    Type listType = new TypeToken<ArrayList<BankAccountLine>>(){}.getType();
                    return new Gson().fromJson(new JsonReader(new InputStreamReader(response.body())), listType);
                },
                (response) -> {
                    throw new RuntimeException("Error getting bank account lines, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Create an internal transfer between accounts.
     *
     * @param bankAccountsTransferModel The transfer model
     * @return The response
     */
    public DolibarrApiResponse createInternalTransfer(BankAccountsTransferModel bankAccountsTransferModel) {
        String json = new Gson().toJson(bankAccountsTransferModel);

        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/bankaccounts/transfer"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return dolibarrClient.execute(request,
                (response) -> new Gson().fromJson(new JsonReader(new InputStreamReader(response.body())), DolibarrApiResponse.class),
                (response) -> {
                    throw new RuntimeException("Error creating internal transfer, response was: " + StringUtil.toString(response.body()));
                });
    }

}
