package org.zerock.foodnamdo.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    // Twilio 계정 정보 초기화
    public static final String ACCOUNT_SID = "ACe30bdd07ba1c58f4b4b132feece69ba4";
    public static final String AUTH_TOKEN = "d18a85c5fcfa58a2c007da854c851213";
    public static final String FROM_PHONE_NUMBER = "010-4557-5569";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(String to, String body) {
        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(FROM_PHONE_NUMBER),
                        body)
                .create();

        System.out.println("Sent message SID: " + message.getSid());
    }
}

