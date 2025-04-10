package ru.mihan;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import ru.nutsalhan87.model.RestResponse;
import ru.nutsalhan87.service.VehicleService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Optional;
import java.util.Properties;

@WebService
@SOAPBinding
public class VehicleWebService {
    private final static int SERVICE_UNAVAILABLE_STATUS = 503;

    private Optional<VehicleService> lookup() {
        VehicleService vehicleService;
        try {
            var jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put("jboss.naming.client.ejb.context", true);
            jndiProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false);
            jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", false);
            jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
            var context = new InitialContext(jndiProperties);
            vehicleService = (VehicleService) context.lookup("ejb:/godbless-service-1.0-plain/vehicle-service!ru.nutsalhan87.service.VehicleService");
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(vehicleService);
    }

    private RestResponse getUnavailableRestResponse() {
        return new RestResponse(SERVICE_UNAVAILABLE_STATUS, "");
    }

    @WebMethod
    @WebResult
    public RestResponse searchByNumberOfWheelsBetween(@WebParam(name = "from") int from, @WebParam(name = "to") int to) {
        return lookup().map(service -> {
            try {
                return service.searchByNumberOfWheelsBetween(from, to);
            } catch (Exception e) {
                e.printStackTrace();
                return getUnavailableRestResponse();
            }
        }).orElseGet(this::getUnavailableRestResponse);
    }

    @WebMethod
    @WebResult
    public RestResponse fixDistance(@WebParam(name = "id") Long id) {
        return lookup().map(service -> {
            try {
                return service.fixDistance(id);
            } catch (Exception e) {
                e.printStackTrace();
                return getUnavailableRestResponse();
            }
        }).orElseGet(this::getUnavailableRestResponse);
    }
}
