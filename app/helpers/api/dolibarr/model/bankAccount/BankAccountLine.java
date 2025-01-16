package helpers.api.dolibarr.model.bankAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jtremeaux
 */
public class BankAccountLine {
    public Integer id;
    public String ref;
    public Date datec;
    public Date dateo;
    public Date datev;
    public BigDecimal amount;
    public String label;
    public String note;
    public String fk_user_author;
    public String fk_user_rappro;
    public String fk_type;
    public String rappro;
    public String num_releve;
    public String num_chq;
    public String bank_chq;
    public String fk_bordereau;
    public String fk_account;
    public String bank_account_label;
    public String emetteur;
    public String import_key;
    List<Object> array_options = new ArrayList<>();
    public String linkedObjectsIds;
    public String canvas;
    public String fk_project;
    public String contact;
    public String contact_id;
    public String thirdparty;
    public String user;
    public String origin;
    public String origin_id;
    public String ref_ext;
    public String statut;
    public String country;
    public String country_id;
    public String country_code;
    public String barcode_type;
    public String barcode_type_code;
    public String barcode_type_label;
    public String barcode_type_coder;
    public String mode_reglement_id;
    public String cond_reglement_id;
    public String cond_reglement;
    public String fk_delivery_address;
    public String shipping_method_id;
    public String modelpdf;
    public String note_public;
    public String total_ht;
    public String total_tva;
    public String total_localtax1;
    public String total_localtax2;
    public String total_ttc;
    public String lines;
    public String fk_incoterms;
    public String libelle_incoterms;
    public String location_incoterms;
    public String name;
    public String lastname;
    public String firstname;
    public String civility_id;
    public String bank_account_ref;
}
