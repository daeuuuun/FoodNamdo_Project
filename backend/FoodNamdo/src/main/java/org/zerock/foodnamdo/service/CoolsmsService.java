package org.zerock.foodnamdo.service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
public class CoolsmsService {

    private final DefaultMessageService messageService;
    public CoolsmsService() {
        this.messageService = NurigoApp.INSTANCE.initialize(
                "NCSQUTYTEAXVV3EG",
                "HTBDJUS6QVMB6RIRZNSF4XU4GS6AOL2Z",
                "https://api.coolsms.co.kr");
    }

    // 인증번호 전송하기
    public void sendSMS(String phone, String code) {

        String cleanphone = phone.replaceAll("[^0-9]", "");

        Message message = new Message();

        message.setFrom("01045575569");
        message.setTo(cleanphone);
        message.setText("[FoodNamdo] 인증번호는 " + code + " 입니다");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

    }

    // 아이디 전송하기
    public void sendIdOrPwd(String phone, String accountId, int IdOrPwd) {

        String cleanphone = phone.replaceAll("[^0-9]", "");

        Message message = new Message();

        String Comment = "";

        if (IdOrPwd == 0){
            Comment = "아이디는 ";
        } else {
            Comment = "비밀번호는 ";
        }

        message.setFrom("01045575569");
        message.setTo(cleanphone);
        message.setText("[FoodNamdo] " + Comment + accountId + " 입니다");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

    }
}
