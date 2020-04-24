package helpers.api.dolibarr.service;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sismics.sapparot.string.StringUtil;
import helpers.api.dolibarr.DolibarrClient;
import helpers.api.dolibarr.model.clientInvoice.ClientInvoiceModel;
import helpers.api.dolibarr.model.clientInvoice.ClientInvoicePaymentModel;
import helpers.api.dolibarr.model.clientInvoice.InvoiceValidateModel;
import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jtremeaux
 */
public class ClientInvoiceService {
    public DolibarrClient dolibarrClient;

    public ClientInvoiceService(DolibarrClient dolibarrClient) {
        this.dolibarrClient = dolibarrClient;
    }

    /**
     * Get the list of client invoices.
     *
     * @return The list of client invoices
     */
    public List<ClientInvoiceModel> listClientInvoice() {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/invoices"))))
                .GET()
                .build();
        return dolibarrClient.execute(request,
                (response) -> {
                    Type listType = new TypeToken<ArrayList<ClientInvoiceModel>>(){}.getType();
                    return dolibarrClient.getGson().fromJson(new JsonReader(new InputStreamReader(response.body())), listType);
                },
                (response) -> {
                    throw new RuntimeException("Error getting client invoice list, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Create a client invoice.
     *
     * @param clientInvoiceModel The client invoice model
     * @return The payment ID
     */
    public Integer createClientInvoice(ClientInvoiceModel clientInvoiceModel) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/invoices"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(dolibarrClient.getGson().toJson(clientInvoiceModel)))
                .build();
        return dolibarrClient.execute(request,
                (response) -> Integer.parseInt(IOUtils.toString(response.body(), StandardCharsets.UTF_8.name())),
                (response) -> {
                    throw new RuntimeException("Error creating client invoice, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Validate a client invoice.
     *
     * @param invoiceId The invoice ID
     * @param invoiceValidateModel The invoice validation model
     */
    public String validateInvoice(Integer invoiceId, InvoiceValidateModel invoiceValidateModel) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/invoices/" + invoiceId + "/validate"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(dolibarrClient.getGson().toJson(invoiceValidateModel)))
                .build();
        return dolibarrClient.execute(request,
                (response) -> null,
                (response) -> {
                    throw new RuntimeException("Error validating invoice, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Pay a client invoice.
     *
     * @param invoiceId The invoice ID
     * @param clientInvoicePaymentModel The payment model
     * @return The payment ID
     */
    public Integer payInvoice(Integer invoiceId, ClientInvoicePaymentModel clientInvoicePaymentModel) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/invoices/" + invoiceId + "/payments"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(dolibarrClient.getGson().toJson(clientInvoicePaymentModel)))
                .build();
        return dolibarrClient.execute(request,
                (response) -> Integer.parseInt(IOUtils.toString(response.body(), StandardCharsets.UTF_8.name())),
                (response) -> {
                    throw new RuntimeException("Error paying invoice, response was: " + StringUtil.toString(response.body()));
                });
    }
}
