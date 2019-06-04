package helpers.api.dolibarr.service;

import com.google.gson.JsonObject;
import com.sismics.sapparot.string.StringUtil;
import helpers.api.dolibarr.DolibarrClient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Date;

/**
 * @author jtremeaux
 */
public class InvoiceService {
    public DolibarrClient dolibarrClient;

    public InvoiceService(DolibarrClient dolibarrClient) {
        this.dolibarrClient = dolibarrClient;
    }

    /**
     * Pay an invoice.
     *
     * @param invoiceId The invoice ID
     * @param accountId The account ID
     * @param payDate The pay date, if null use today's date
     */
    public void payInvoice(Integer invoiceId, Integer accountId, Date payDate) {
        // FIXME Dolibarr doesn't have this API yet.
        if (payDate == null) {
            payDate = new Date();
        }
        JsonObject body = new JsonObject();
        body.addProperty("datepaye", payDate.getTime());
        body.addProperty("paiementid", 3);
        body.addProperty("closepaidinvoices", "yes");
        body.addProperty("accountid", accountId);

        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/supplierinvoices/" + invoiceId + "/payments"))))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        dolibarrClient.execute(request, null,
                (response) -> {
                    throw new RuntimeException("Error paying invoice, response was: " + StringUtil.toString(response.body()));
                });
    }
}
