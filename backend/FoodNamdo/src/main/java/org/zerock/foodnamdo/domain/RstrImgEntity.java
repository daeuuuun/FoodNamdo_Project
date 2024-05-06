package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rstr_img")
public class RstrImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rstr_img_id")
    private Long rstrImgId;

    @ManyToOne
    @JoinColumn(name = "rstr_id")
    private RstrEntity rstrEntity;

    @Column(name = "rstr_img_url", nullable = false)
    private String rstrImgUrl;
}
