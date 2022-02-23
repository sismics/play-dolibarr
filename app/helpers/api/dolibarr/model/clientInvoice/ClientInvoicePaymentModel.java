package helpers.api.dolibarr.model.clientInvoice;

import java.util.Date;

/**
 * @author jtremeaux
 */
public class ClientInvoicePaymentModel {
    public Date datepaye;
    public Integer paymentid;
    public String closepaidinvoices = "yes";
    public Integer accountid;
    public String num_paiement;
    public String comment;
    public String chqemetteur;
    public String chqbank;
}
