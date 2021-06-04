package helpers.api.dolibarr.service;

import com.google.gson.Gson;
import com.sismics.sapparot.string.StringUtil;
import helpers.api.dolibarr.DolibarrClient;
import helpers.api.dolibarr.model.supplierInvoice.SupplierinvoicesAddPaymentModel;
import org.apache.commons.io.IOUtils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author jtremeaux
 */
public class SupplierInvoiceService {
    public DolibarrClient dolibarrClient;

    public SupplierInvoiceService(DolibarrClient dolibarrClient) {
        this.dolibarrClient = dolibarrClient;
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
