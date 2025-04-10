package ru.nutsalhan87.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jboss.ejb3.annotation.Pool;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import ru.nutsalhan87.model.RestResponse;
import ru.nutsalhan87.model.VehicleDto;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

@Stateless(name = "vehicle-service")
@Remote(VehicleService.class)
@Pool("vehicle-service-pool")
public class VehicleServiceImpl implements VehicleService {
    private final String apiPath;

    public VehicleServiceImpl() {
        this.apiPath = System.getenv("JAXBU_URL");
        if (this.apiPath == null) {
            throw new RuntimeException("JAXBU_URL environment variable is not set");
        }
    }

    @Override
    public RestResponse searchByNumberOfWheelsBetween(int from, int to) throws Exception {
        // Construct the URL for the request
        String url = String.format("%s/vehicles", apiPath);

        String vehicleNumberOfWheelsFieldName = "numberOfWheels";
        String filterValue = vehicleNumberOfWheelsFieldName + ">=" + from + "," + vehicleNumberOfWheelsFieldName + "<=" + to;
        String encodedFilterValue = UriUtils.encode(filterValue, StandardCharsets.UTF_8);

        // Build the query URI with the appropriate filter
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("filter", encodedFilterValue)
                .build(true) // This ensures the query is properly encoded
                .toUriString(); // This returns the URI as a string

        // Load keystore (this is where your SSL certificate is stored)
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (InputStream keyStoreStream = getClass().getClassLoader().getResourceAsStream("keystore.jks")) {
            if (keyStoreStream == null) {
                throw new RuntimeException("Keystore not found in classpath");
            }
            keyStore.load(keyStoreStream, "secret".toCharArray());  // Replace "changeit" with your keystore password
        }

        // Build SSLContext using the keystore
        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, "secret".toCharArray())  // Load key material (certificate)
                .loadTrustMaterial(keyStore, null)  // Trust all certs in the keystore
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Prepare HttpClient with SSL and NoopHostnameVerifier (disabling hostname verification for simplicity)
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build()) {
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            return new RestResponse(statusCode, responseString);
        } catch (Exception e) {
            // Log exception (can also improve error handling here)
            e.printStackTrace();
            return new RestResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "");
        }
    }

    @Override
    public RestResponse fixDistance(Long id) {
        String url = String.format("%s/vehicles/%d", apiPath, id);

        CloseableHttpClient httpClient = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            // Load keystore (this is where your SSL certificate is stored)
            KeyStore keyStore = KeyStore.getInstance("JKS");
            try (InputStream keyStoreStream = getClass().getClassLoader().getResourceAsStream("keystore.jks")) {
                if (keyStoreStream == null) {
                    throw new RuntimeException("Keystore not found in classpath");
                }
                keyStore.load(keyStoreStream, "secret".toCharArray());
            }

            // Build SSLContext using the keystore
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadKeyMaterial(keyStore, "secret".toCharArray())  // Load key material (certificate)
                    .loadTrustMaterial(keyStore, null)  // Trust all certs in the keystore
                    .build();

            // Prepare the HttpClient with SSL and hostname verifier
            httpClient = HttpClients.custom()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
            HttpPatch httpPatch = new HttpPatch(url);
            httpPatch.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            VehicleDto vehicleDto = VehicleDto.builder().distanceTravelled(0).build();
            String jsonBody = objectMapper.writeValueAsString(vehicleDto);
            StringEntity entity = new StringEntity(jsonBody);
            httpPatch.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPatch);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity);
            return new RestResponse(statusCode, responseString);
        } catch (Exception e) {
            // Log exception for debugging
            e.printStackTrace();
            return new RestResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "");
        } finally {
            // Close the HttpClient to release resources
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

