package org.zerock.foodnamdo.customDTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String accountId;
    private String password;
}