package no.ssb.api.amadeus.util;

import com.amadeus.Params;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CommonUtils {

    public static void addParamIfNotNull(Params params, String name, String value) {
        if (value != null && value.length() > 0) {
            params.and(name, value);
        }
    }

    public static String getNextLink(String body) {
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        return (jsonObject.getAsJsonObject("meta") != null
                && jsonObject.getAsJsonObject("meta").getAsJsonObject("links") != null
                && jsonObject.getAsJsonObject("meta").getAsJsonObject("links").get("next") != null) ?
                jsonObject.getAsJsonObject("meta").getAsJsonObject("links").get("next").getAsString() : null;
    }

}
