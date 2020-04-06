package helpers.api.dolibarr.model.supplierInvoice;

import java.util.Date;

/**
 * @author jtremeaux
 */
public class SupplierinvoicesAddPaymentModel {
    public Long datepaye = new Date().getTime() / 1000; /* Unix timestamp */
    public Integer paiementid; /* Payment method */
    public String closepaidinvoices = "yes";
    public Integer accountid;
    public String num_paiement;
    public String comment;
    public String chqemetteur;
    public String chqbank;
}
