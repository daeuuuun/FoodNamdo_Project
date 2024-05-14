package org.zerock.foodnamdo.domain;

import lombok.*;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
//import org.springframework.security.core.GrantedAuthority;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "userEntity")
//    @JsonManagedReference
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "userEntity")
//    @JsonManagedReference
    private List<FavoriteEntity> favorites;

    @OneToMany(mappedBy = "userEntity")
//    @JsonManagedReference
    private List<ReactionEntity> reactions;

    @OneToMany(mappedBy = "userEntity")
//    @JsonManagedReference
    private List<UserBadgeEntity> userBadges;

    @OneToMany(mappedBy = "userEntity")
//    @JsonManagedReference
    private List<UserPreferenceEntity> preferences;


    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
