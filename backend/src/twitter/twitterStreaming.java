package twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;//??????

public class twitterStreaming {

    private jestCloud uploadData = new jestCloud();



    void fetchTwits() {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("62G2zLGAKXM25TUyXeGJgNZpY")
                .setOAuthConsumerSecret("XecKmb0ifHlhO8Y5qn0CKH7tr2orDPuObDDdTOtWX9apFw9qUT")
                .setOAuthAccessToken("2989290723-lHgusR0oVRtvMam5JTK35noAyjucMcEzdUBzstY")
                .setOAuthAccessTokenSecret("GTzDl9kAq9Wni1lAOdPJ7HJJC6T2BjonhCVrtqJ3O1e5E");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                if (status.getGeoLocation() != null && status.getUser() != null) {
                    System.out.print(status);
                    uploadData.uploadTweet(status);
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        twitterStream.addListener(listener);
        twitterStream.sample();
    }

    public static void main(String[] args) {
        twitterStreaming fetcher = new twitterStreaming();
        fetcher.fetchTwits();
    }
}
