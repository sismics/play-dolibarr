package helpers.api.dolibarr.model.supplierInvoice;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jtremeaux
 */
public class SupplierInvoiceModel {
    public String ref;
    public String ref_supplier;
    public String label;
    public Integer socid;
    public Long type;
    public Long statut;
    public Long status;
    public String close_code;
    public String close_note;
    public String paye;
    public String datec;
    public String tms;
    public String date;
    public String date_echeance;
    public String amount;
    public String remise;
    public String tva;
    public String localtax1;
    public String localtax2;
    public BigDecimal total_ht;
    public BigDecimal total_tva;
    public String total_localtax1;
    public String total_localtax2;
    public BigDecimal total_ttc;
    public String note_private;
    public String note_public;
    public String propalid;
    public Integer cond_reglement_id;
    public String cond_reglement_code;
    public String cond_reglement_label;
    public String cond_reglement_doc;
    public Integer fk_account;
    public Integer mode_reglement_id;
    public String mode_reglement_code;
    public Long transport_mode_id;
//            "extraparams": [],
    public List<SupplierInvoiceLineModel> lines = new ArrayList<>();
    public String fournisseur;
    public String fk_multicurrency;
    public String multicurrency_code;
    public BigDecimal multicurrency_tx;
    public BigDecimal multicurrency_total_ht;
    public BigDecimal multicurrency_total_tva;
    public BigDecimal multicurrency_total_ttc;
    public Long fk_facture_source;
    public String fac_rec;
    public String totalpaid;
    public String totaldeposits;
    public String totalcreditnotes;
    public String sumpayed;
    public String sumpayed_multicurrency;
    public String sumdeposit;
    public String sumdeposit_multicurrency;
    public String sumcreditnote;
    public String sumcreditnote_multicurrency;
    public String remaintopay;
    public Long id;
    public Long entity;
    public String import_key;
//    public String array_options": [],
    public String array_languages;
    public String contacts_ids;
    public String linked_objects;
    public String linkedObjectsIds;
    public String canvas;
    public String fk_project;
    public String contact_id;
    public String user;
    public String origin;
    public String origin_id;
    public String ref_ext;
    public String country_id;
    public String country_code;
    public String state_id;
    public String region_id;
    public String demand_reason_id;
    public String shipping_method_id;
    public String model_pdf;
    public String last_main_doc;
    public String fk_bank;
    public String name;
    public String lastname;
    public String firstname;
    public String civility_id;
    public String date_creation;
    public String date_validation;
    public String date_modification;
    public String date_cloture;
    public String user_author;
    public String user_creation;
    public String user_creation_id;
    public String user_valid;
    public String user_validation;
    public String user_validation_id;
    public String user_closing_id;
    public String user_modification;
    public String user_modification_id;
    public Long specimen;
    public Long fk_incoterms;
    public String label_incoterms;
    public String location_incoterms;
    public String fk_soc;
    public Long datep;
    public String libelle;
    public Long paid;
    public Long fk_statut;
    public Long fk_user_author;
    public Long fk_user_valid;
    public String fk_fac_rec_source;
    public String socnom;

    public static final String DATE_SHORT_FORMAT = "yyyy-MM-dd";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_SHORT_FORMAT);

    public SupplierInvoiceModel() {
    }

    public SupplierInvoiceModel(String ref_supplier, Integer socid, Date date, Date dateEcheance, Integer cond_reglement_id, Integer mode_reglement_id, Integer fk_account) {
        this.ref = "auto";
        this.ref_supplier = ref_supplier;
        this.socid = socid;
        this.date = DATE_TIME_FORMATTER.print(new DateTime(date).withZone(DateTimeZone.UTC));
        this.date_echeance = DATE_TIME_FORMATTER.print(new DateTime(dateEcheance).withZone(DateTimeZone.UTC));
        this.cond_reglement_id = cond_reglement_id;
        this.mode_reglement_id = mode_reglement_id;
        this.fk_account = fk_account;
    }
}
