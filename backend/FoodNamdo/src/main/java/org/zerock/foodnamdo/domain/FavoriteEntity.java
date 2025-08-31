package org.zerock.foodnamdo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
//    @JsonBackReference
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "rstr_id")
//    @JsonBackReference
    private RstrEntity rstrEntity;
//    private RstrEntity rstrId;
}
