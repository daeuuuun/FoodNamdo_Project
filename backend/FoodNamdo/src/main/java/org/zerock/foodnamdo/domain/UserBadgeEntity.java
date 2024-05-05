package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private BadgeEntity badge;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "badge_date", nullable = false)
    private Date badgeDate;
}
