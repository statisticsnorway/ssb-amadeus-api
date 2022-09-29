package no.ssb.api.amadeus.controller;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.Response;
import com.amadeus.exceptions.ResponseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import no.ssb.api.amadeus.util.FlightOfferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lrb on 06.02.2017.
 */
@Api("ssb-amadeus-api")
@RestController
@RequestMapping("")
public class FlightPricesController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${amadeus.api.flighturl}")
    private String baseurl;

    @Value("${amadeus.api.key}")
    private String amadeusapikey;

    @Value("${amadeus.api.secret}")
    private String amadeusapisecret;

    @Value("${amadeus.api.runenv}")
    private String amadeusapirunenv;

    @Value("${amadeus.api.flightmax}")
    private String max;

    @ApiOperation(value = "Ping metode for å kunne sjekke at API'et er oppe")
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<?> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @ApiOperation(value = "Søke etter billige flypriser")
    @RequestMapping(value ="/flight-offers", method = RequestMethod.GET)
    public ResponseEntity<String> flightOffers(@RequestParam String origin,
                                               @RequestParam String destination,
                                               @RequestParam String departureDate,
                                               @RequestParam(required = false) String returnDate,
                                               @RequestParam(required = false) String travelClass,
                                               @RequestParam(required = false) String adults,
                                               @RequestParam(required = false) String children,
                                               @RequestParam(required = false) String infants,
                                               @RequestParam(required = false) String seniors,
                                               @RequestParam(required = false) String nonstop,
                                               @RequestParam(required = false) String currency,
                                               @RequestParam(required = false) String includeAirlineCodes,
                                               @RequestParam(required = false) String excludeAirlineCodes,
                                               @RequestParam(required = false) String numResult
    ) {
//        log.info("amadeusapikey: {}, amadeusapisecret:{}, baseurl:{}", amadeusapikey, amadeusapisecret, baseurl);
        log.info("baseurl:{}, travelClass:{}", baseurl, travelClass);
        Amadeus amadeus = Amadeus.builder(amadeusapikey, amadeusapisecret).setHostname(amadeusapirunenv).build();
        Params params = FlightOfferUtils.createFlightOfferParams(origin, destination, departureDate, returnDate,
                travelClass, adults, children, infants, seniors, nonstop, currency,
                includeAirlineCodes, excludeAirlineCodes,
                numResult != null && !numResult.equals("-") && !numResult.equals("0") ? numResult : max );
        log.info("Params: {}", params.toString());
        try {
//            log.info("hostname: " + amadeus.getConfiguration().getHostname());
//            log.info("host: " + amadeus.getConfiguration().getHost());
            Response  response = amadeus.get(baseurl, params);
            log.info("{}: {}", response.getStatusCode(), response.getBody());
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (ResponseException e) {
            log.error("Feil i kall til Amadeus flight-offer: {} ,({})", e.getLocalizedMessage(), e.getResponse().getStatusCode());
            return new ResponseEntity<>("Noe feilet i ssb-amadeus - " + e.getResponse().getStatusCode() + ": " + e.getLocalizedMessage(), HttpStatus.valueOf(e.getResponse().getStatusCode()));
        }

    }


}
