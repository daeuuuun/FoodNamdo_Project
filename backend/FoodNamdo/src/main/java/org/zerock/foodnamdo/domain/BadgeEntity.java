package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "badge")
public class BadgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long badgeId;

    @Column(name = "badge_name", nullable = false)
    private String badgeName;

    @Column(name = "badge_comment", nullable = false)
    private String badgeComment;

    @Column(name = "badge_unlock", nullable = false)
    private String badgeUnlock;

    @OneToMany(mappedBy = "badgeEntity", cascade = CascadeType.ALL)
    private List<UserBadgeEntity> userBadges;
}
