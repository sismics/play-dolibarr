package helpers.api.dolibarr.model.supplierInvoice;

import java.math.BigDecimal;

/**
 * @author jtremeaux
 */
public class SupplierInvoiceLineModel {
    public String ref;
    public String product_ref;
    public String ref_supplier;
    public String product_desc;
    public BigDecimal pu_ht;
    public BigDecimal subprice;
    public BigDecimal pu_ttc;
    public Long fk_facture_fourn;
    public String label;
    public String description;
    public String date_start;
    public String date_end;
    public String situation_percent;
    public Long fk_prev_id;
    public String vat_src_code;
    public BigDecimal tva_tx;
    public BigDecimal localtax1_tx;
    public BigDecimal localtax2_tx;
    public BigDecimal qty;
    public BigDecimal remise_percent;
    public BigDecimal total_ht;
    public BigDecimal total_ttc;
    public BigDecimal total_tva;
    public BigDecimal total_localtax1;
    public BigDecimal total_localtax2;
    public Long fk_product;
    public Integer product_type;
    public String product_label;
    public String info_bits;
    public Long fk_remise_except;
    public Long fk_parent_line;
    public String special_code;
    public String rang;
    public String localtax1_type;
    public Long localtax2_type;
    public BigDecimal multicurrency_subprice;
    public BigDecimal multicurrency_total_ht;
    public BigDecimal multicurrency_total_tva;
    public BigDecimal multicurrency_total_ttc;
    public Long id;
    public Long fk_unit;
    public String date_debut_prevue;
    public String date_debut_reel;
    public String date_fin_prevue;
    public String date_fin_reel;
    public String weight;
    public String weight_units;
    public String width;
    public String width_units;
    public String height;
    public String height_units;
    public String length;
    public String length_units;
    public String surface;
    public String surface_units;
    public String volume;
    public String volume_units;
    public String multilangs;
    public String desc;
    public String product;
    public String product_barcode;
    public Long fk_product_type;
    public String duree;
    public String entity;
    public String import_key;
    public String contacts_ids;
    public String linked_objects;
    public String linkedObjectsIds;
    public String canvas;
    public String origin;
    public String origin_id;
    public String ref_ext;
    public String statut;
    public String status;
    public String state_id;
    public String region_id;
    public String demand_reason_id;
    public String transport_mode_id;
    public String last_main_doc;
    public Long fk_bank;
    public Long fk_account;
    public String lines;
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
    public String specimen;
    public String libelle;
    public String code_ventilation;
    public Long fk_accounting_account;

    public SupplierInvoiceLineModel() {
    }

    public SupplierInvoiceLineModel(Integer product_type, BigDecimal pu_ht, BigDecimal tva_tx, BigDecimal qty, String description) {
        this.product_type = product_type;
        this.pu_ht = pu_ht;
        this.tva_tx = tva_tx;
        this.qty = qty;
        this.description = description;
    }
}
