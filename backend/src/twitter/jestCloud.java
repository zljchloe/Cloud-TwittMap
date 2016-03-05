package twitter;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;


import static org.elasticsearch.common.xcontent.XContentFactory.*;
import twitter4j.Status;

import java.io.IOException;
import java.net.UnknownHostException;

public class jestCloud {
	
    private keywords keywordHelper = new keywords();

    public void uploadTweet(Status status ) {
        String index = keywordHelper.keyword(status.getText());
        JestClient client = null;
        if (index != null) {
            try {
                JestClientFactory factory = new JestClientFactory();
                factory.setHttpClientConfig(new HttpClientConfig
                        .Builder("https://search-cloud-assignment1-ujdbxi2poa2dtihyle62cn7wsa.us-east-1.es.amazonaws.com")
                        .multiThreaded(true)
                        .build());
                client = factory.getObject();

                String source = jsonBuilder()
                        .startObject()
                        .field("user", status.getUser().getName())
                        .field("timestamp", status.getCreatedAt().toString())
                        .field("text", status.getText())
                        .field("keyword",index)
                        .field("latitude", status.getGeoLocation().getLatitude())
                        .field("longtitude",status.getGeoLocation().getLongitude())
                        .field("url", status.getSource())
                        .endObject().string();
                Index putIndex = new Index.Builder(source).index(index).type("tweet").build();
                client.execute(putIndex);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}