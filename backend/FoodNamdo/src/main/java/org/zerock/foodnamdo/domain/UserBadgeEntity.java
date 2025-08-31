package org.zerock.foodnamdo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_badge")
public class UserBadgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_badge_id")
    private Long userBadgeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "badge_id")
//    @JsonBackReference
    private BadgeEntity badgeEntity;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "badge_date", nullable = false)
//    private LocalDateTime badgeDate;

    @Column(name = "badge_on_off")
    private Boolean badgeOnOff;

//    @Column(name = "badge_sequence")
//    private int badgeSequence;
}
