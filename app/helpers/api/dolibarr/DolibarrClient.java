package helpers.api.dolibarr;

import com.sismics.sapparot.function.CheckedConsumer;
import com.sismics.sapparot.function.CheckedFunction;
import com.sismics.sapparot.http.HttpHelper;
import helpers.api.dolibarr.service.BankAccountsService;
import helpers.api.dolibarr.service.InvoiceService;
import helpers.api.dolibarr.service.ThirdPartyService;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Play;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

import static org.mockito.Mockito.mock;

/**
 * @author jtremeaux
 */
public class DolibarrClient {
    private HttpClient client;

    private static DolibarrClient dolibarrClient;

    private BankAccountsService bankAccountsService;

    private InvoiceService invoiceService;

    private ThirdPartyService thirdPartyService;

    private static final DateTimeFormatter DOLIBARR_DATE_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy");

    public static DolibarrClient get() {
        if (dolibarrClient == null) {
            dolibarrClient = new DolibarrClient();
        }
        return dolibarrClient;
    }

    public DolibarrClient() {
        client = createClient();
        if (isMock()) {
            bankAccountsService = mock(BankAccountsService.class);
            invoiceService = mock(InvoiceService.class);
            thirdPartyService = mock(ThirdPartyService.class);
        } else {
            bankAccountsService = new BankAccountsService(this);
            invoiceService = new InvoiceService(this);
            thirdPartyService = new ThirdPartyService(this);
        }
    }

    private boolean isMock() {
        return Boolean.parseBoolean(Play.configuration.getProperty( "dolibarr.mock", "false"));
    }

    private static HttpClient createClient() {
        return HttpClient.newBuilder().build();
    }

    public String getDolibarrApiUrl() {
        return Play.configuration.getProperty("dolibarr.api.url");
    }

    public String getDolapikey() {
        return Play.configuration.getProperty("dolibarr.dolapikey");
    }

    public String getUrl(String url) {
        return getDolibarrApiUrl() + url;
    }

    public HttpClient getClient() {
        return client;
    }

    public BankAccountsService getBankAccountsService() {
        return bankAccountsService;
    }

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public ThirdPartyService getThirdPartyService() {
        return thirdPartyService;
    }

    public HttpRequest.Builder authRequest(HttpRequest.Builder builder) {
        return builder
                .header("DOLAPIKEY", getDolapikey());
    }

    public <T> T execute(HttpRequest request, CheckedFunction<HttpResponse<InputStream>, T> onSuccess, CheckedConsumer<HttpResponse<InputStream>> onFailure) {
        return HttpHelper.execute(getClient(), request, onSuccess, onFailure);
    }

    public String formatDate(Date date) {
        return DOLIBARR_DATE_FORMATTER.withZone(DateTimeZone.UTC).print(date.getTime());
    }
}
