package helpers.api.dolibarr.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sismics.sapparot.string.StringUtil;
import helpers.api.dolibarr.DolibarrClient;
import helpers.api.dolibarr.model.ThirdParty;
import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jtremeaux
 */
public class ThirdPartyService {
    public DolibarrClient dolibarrClient;

    public ThirdPartyService(DolibarrClient dolibarrClient) {
        this.dolibarrClient = dolibarrClient;
    }

    /**
     * Get the list of third parties.
     *
     * @return The list of third parties
     */
    public List<ThirdParty> listThirdParty() {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/thirdparties"))))
                .GET()
                .build();
        return dolibarrClient.execute(request,
                (response) -> {
                    Type listType = new TypeToken<ArrayList<ThirdParty>>(){}.getType();
                    return new Gson().fromJson(new JsonReader(new InputStreamReader(response.body())), listType);
                },
                (response) -> {
                    throw new RuntimeException("Error getting third party list, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Get the details of a third party.
     *
     * @param id The third party id
     * @return The details of the third party
     */
    public ThirdParty getThirdParty(Integer id) {
        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/thirdparties/" + id))))
                .GET()
                .build();
        return dolibarrClient.execute(request,
                (response) -> new Gson().fromJson(new JsonReader(new InputStreamReader(response.body())), ThirdParty.class),
                (response) -> {
                    throw new RuntimeException("Error getting third party detail, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Create a third party.
     *
     * @param thirdParty The third party to create
     * @return The third party ID
     */
    public Integer createThirdParty(ThirdParty thirdParty) {
        String json = new Gson().toJson(thirdParty);

        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/thirdparties"))))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return dolibarrClient.execute(request,
                (response) -> Integer.parseInt(IOUtils.toString(response.body(), StandardCharsets.UTF_8.name())),
                (response) -> {
                    throw new RuntimeException("Error creating third party, response was: " + StringUtil.toString(response.body()));
                });
    }

    /**
     * Update a third party.
     *
     * @param thirdParty The third party to update
     * @return The updated third party
     */
    public ThirdParty updateThirdParty(ThirdParty thirdParty) {
        String json = new Gson().toJson(thirdParty);

        HttpRequest request = dolibarrClient.authRequest(HttpRequest.newBuilder()
                .uri(URI.create(dolibarrClient.getUrl("/thirdparties/" +thirdParty.id))))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return dolibarrClient.execute(request,
                (response) -> new Gson().fromJson(new JsonReader(new InputStreamReader(response.body())), ThirdParty.class),
                (response) -> {
                    throw new RuntimeException("Error updating third party, response was: " + StringUtil.toString(response.body()));
                });
    }
}
