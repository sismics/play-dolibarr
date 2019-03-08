package helpers.api.dolibarr;

import com.sismics.sapparot.function.CheckedConsumer;
import com.sismics.sapparot.function.CheckedFunction;
import com.sismics.sapparot.okhttp.OkHttpHelper;
import helpers.api.dolibarr.service.BankAccountsService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import play.Play;

import static org.mockito.Mockito.mock;

/**
 * @author jtremeaux
 */
public class DolibarrClient {
    private OkHttpClient client;

    private static DolibarrClient dolibarrClient;

    private BankAccountsService bankAccountsService;

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
        } else {
            bankAccountsService = new BankAccountsService(this);
        }
    }

    private boolean isMock() {
        return Boolean.parseBoolean(Play.configuration.getProperty( "dolibarr.mock", "false"));
    }

    private static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .build();
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

    public OkHttpClient getClient() {
        return client;
    }

    public BankAccountsService getBankAccountsService() {
        return bankAccountsService;
    }

    public Request authRequest(Request request) {
        return request.newBuilder()
                .addHeader("DOLAPIKEY", getDolapikey())
                .build();
    }

    public <T> T execute(Request request, CheckedFunction<Response, T> onSuccess, CheckedConsumer<Response> onFailure) {
        return OkHttpHelper.execute(getClient(), request, onSuccess, onFailure);
    }
}
