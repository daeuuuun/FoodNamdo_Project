package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorite")
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long favoriteId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "rstr_id")
    private RstrEntity restaurant;
//    private RstrEntity rstrId;
}
