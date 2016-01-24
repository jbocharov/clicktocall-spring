package com.twilio.clicktocall;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.CallFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan
@Configuration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public TwilioRestClient getTwilioRestClient(@Value("${TWILIO_ACCOUNT_SID}") String accountSid,
                                                @Value("${TWILIO_AUTH_TOKEN}") String authToken) {
        TwilioRestClient twilioRestClient = new TwilioRestClient(accountSid, authToken);
        return twilioRestClient;
    }

    @Bean
    public CallFactory getCallFactory(TwilioRestClient twilioRestClient){
        return twilioRestClient.getAccount().getCallFactory();
    }
}
