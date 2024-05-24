package org.zerock.foodnamdo.baseDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class APIUserDTO extends User {
    private String accountId;
    private String password;

    public APIUserDTO(String accountId, String password, Collection<GrantedAuthority> authorities) {
        super(accountId, password, authorities);
        this.accountId = accountId;
        this.password = password;
    }
}
