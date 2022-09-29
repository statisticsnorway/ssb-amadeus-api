package no.ssb.api.amadeus.util;

import com.amadeus.Params;
import com.amadeus.resources.FlightOffer;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlightOfferUtils {
    private static final Logger log = LoggerFactory.getLogger(FlightOfferUtils.class);
    public static Params createFlightOfferParams(String origin, String destination, String departureDate, String returnDate,
                                                 String travelClass, String adults, String children, String infants, String seniors,
                                                 String nonstop, String currency, String includeAirlineCodes, String excludeAirlineCodes,
                                                 String max) {
        Params params = Params
                .with("originLocationCode", origin)
                .and("destinationLocationCode", destination)
                .and("departureDate", departureDate)
                .and("max", max != null ? Integer.valueOf(max) : 50);
        CommonUtils.addParamIfNotNull(params, "returnDate", returnDate);
        CommonUtils.addParamIfNotNull(params, "travelClass", travelClass);
        CommonUtils.addParamIfNotNull(params, "adults", adults);
        CommonUtils.addParamIfNotNull(params, "children", children);
        CommonUtils.addParamIfNotNull(params, "infants", infants);
//        CommonUtils.addParamIfNotNull(params, "seniors", seniors);
        CommonUtils.addParamIfNotNull(params, "includedAirlineCodes", includeAirlineCodes != null ? includeAirlineCodes.replace("#", ","):null);
        CommonUtils.addParamIfNotNull(params, "excludedAirlineCodes", excludeAirlineCodes != null ? excludeAirlineCodes.replace("#", ","):null);
        params.and("nonStop", "true".equalsIgnoreCase(nonstop));
        params.and("currencyCode", (currency != null && currency.length() > 0) ? currency : "NOK");
        return params;
    }

    public static JsonArray flightOffersToJsonArray(FlightOffer[] flightOffers) {
        JsonArray flightOffersJson = new JsonArray();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd' 'HH:mm:ss").create();
        for (FlightOffer fo : flightOffers) {
            JsonElement jsonElement = gson.toJsonTree(fo);
            JsonObject jsonObject = (JsonObject)jsonElement;
            flightOffersJson.add(jsonObject);
        }
        log.info("flightoffers: {}", flightOffersJson);
        return flightOffersJson;
    }


}
