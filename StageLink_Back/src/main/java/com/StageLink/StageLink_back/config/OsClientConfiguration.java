package com.StageLink.StageLink_back.config;

import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;

@Configuration
public class OsClientConfiguration {

    // Path to OCI config file
    String configurationFilePath = "config";
    String profile = "DEFAULT";

    public ObjectStorage getObjectStorage() throws IOException {
        // Set the HTTP provider explicitly
        System.setProperty("com.oracle.bmc.sdk.http.provider", "com.oracle.bmc.http.client.jersey.JerseyHttpProvider");

        // Load config file
        final ConfigFileReader.ConfigFile configFile =
                ConfigFileReader.parse(configurationFilePath, profile);

        final ConfigFileAuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(configFile);

        // Build and return the Object Storage client
        return ObjectStorageClient.builder().build(provider);
    }
}
