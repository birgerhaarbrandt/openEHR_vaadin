package org.vaadin.example.datasource;

import org.ehrbase.openehr.sdk.client.openehrclient.OpenEhrClient;
import org.ehrbase.openehr.sdk.client.openehrclient.OpenEhrClientConfig;
import org.ehrbase.openehr.sdk.client.openehrclient.defaultrestclient.DefaultRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.URI;
import java.net.URISyntaxException;


@Configuration
public class SDKConfiguration {


    @Value("${ehrbase.uri}")
    private String ehrbaseUri;

    @Bean
    public OpenEhrClient getOpenEhrClient() throws URISyntaxException {

        String OPEN_EHR_URL = ehrbaseUri + "/ehrbase/";
        OpenEhrClientConfig config = new OpenEhrClientConfig(new URI(OPEN_EHR_URL));
        return new DefaultRestClient(config);
    }
}
