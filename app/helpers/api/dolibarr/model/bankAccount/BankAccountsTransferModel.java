package helpers.api.dolibarr.model.bankAccount;

import java.math.BigDecimal;

/**
 * @author jtremeaux
 */
public class BankAccountsTransferModel {
    public Integer bankaccount_from_id;
    public Integer bankaccount_to_id;
    public Long date; // Unix timestamp
    public String description;
    public BigDecimal amount;
    public BigDecimal amount_to;
}
