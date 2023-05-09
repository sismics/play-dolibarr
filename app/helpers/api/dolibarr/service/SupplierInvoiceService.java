package helpers.api.dolibarr.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sismics.sapparot.string.StringUtil;
import helpers.api.dolibarr.DolibarrClient;
import helpers.api.dolibarr.model.supplierInvoice.SupplierInvoiceModel;
import helpers.api.dolibarr.model.supplierInvoice.SupplierInvoiceValidateModel;
import helpers.api.dolibarr.model.supplierInvoice.SupplierinvoicesAddPaymentModel;
import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jtremeaux
 */
public class SupplierInvoiceService {
    public DolibarrClient dolibarrClient;

    public SupplierInvoiceService(DolibarrClient dolibarrClient) {
        this.dolibarrClient = dolibarrClient;
    }

    /**
     * Get the list of supplier invoices.
     *
     * @return The list of supplier invoices
     */
    public List<SupplierInvoiceModel> listSupplierInvoice() {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/supplierinvoices"))))
                .GET()
                .build();
        return dolibarrClient.execute(request,
                (response) -> {
                    Type listType = new TypeToken<ArrayList<SupplierInvoiceModel>>(){}.getType();
                    return dolibarrClient.getGson().fromJson(new JsonReader(new InputStreamReader(response.body())), listType);
                },
                (response) -> {
                    throw new RuntimeException("Error getting supplier invoice list, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Create a supplier invoice.
     *
     * @param supplierInvoiceModel The supplier invoice model
     * @return The payment ID
     */
    public Integer createSupplierInvoice(SupplierInvoiceModel supplierInvoiceModel) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/supplierinvoices"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(dolibarrClient.getGson().toJson(supplierInvoiceModel)))
                .build();
        return dolibarrClient.execute(request,
                (response) -> Integer.parseInt(IOUtils.toString(response.body(), StandardCharsets.UTF_8.name())),
                (response) -> {
                    throw new RuntimeException("Error creating supplier invoice, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Validate a supplier invoice.
     *
     * @param invoiceId The invoice ID
     * @param supplierInvoiceValidateModel The validation model
     */
    public String validateInvoice(Integer invoiceId, SupplierInvoiceValidateModel supplierInvoiceValidateModel) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/supplierinvoices/" + invoiceId + "/validate"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(supplierInvoiceValidateModel)))
                .build();
        return dolibarrClient.execute(request,
                (response) -> null,
                (response) -> {
                    throw new RuntimeException("Error validating invoice, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Pay a supplier invoice.
     *
     * @param invoiceId The invoice ID
     * @param paymentModel The payment model
     * @return The payment ID
     */
    public Integer payInvoice(Integer invoiceId, SupplierinvoicesAddPaymentModel paymentModel) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/supplierinvoices/" + invoiceId + "/payments"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(paymentModel)))
                .build();
        return dolibarrClient.execute(request,
                (response) -> Integer.parseInt(IOUtils.toString(response.body(), StandardCharsets.UTF_8.name())),
                (response) -> {
                    throw new RuntimeException("Error paying invoice, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Pay a supplier invoice.
     *
     * @param invoiceId The invoice ID
     * @param paymentMode The payment mode
     * @return The payment ID
     */
    public Integer payInvoice(Integer invoiceId, Integer accountId, Integer paymentMode, Date paymentDate) {
        var model = new SupplierinvoicesAddPaymentModel();
        model.payment_mode_id = paymentMode;
        model.accountid = accountId;
        if (paymentDate != null) {
            model.datepaye = paymentDate.getTime() / 1000;
        }
        return payInvoice(invoiceId, model);
    }
}
