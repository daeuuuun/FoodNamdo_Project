package org.zerock.foodnamdo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "rstrId")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "rstr")
public class RstrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rstr_id")
    private Long rstrId;

    @Column(name = "rstr_num", nullable = false)
    private int rstrNum;

    @Column(name = "rstr_region", nullable = false, length = 10)
    private String rstrRegion;

    @Column(name = "rstr_permission", nullable = false, length = 255)
    private String rstrPermission;

    @Column(name = "rstr_name", nullable = false, length = 255)
    private String rstrName;

    @Column(name = "rstr_address", nullable = false, length = 255)
    private String rstrAddress;

    @Column(name = "rstr_la", nullable = false)
    private Double rstrLa;

    @Column(name = "rstr_lo", nullable = false)
    private Double rstrLo;

    @Column(name = "rstr_tel", nullable = false, length = 255)
    private String rstrTel;

    @Column(name = "rstr_intro", nullable = false, columnDefinition = "TEXT")
    private String rstrIntro;

    @Column(name = "rstr_naver_rating", nullable = true)
    private Double rstrNaverRating;

    @Column(name = "rstr_review_rating", nullable = true)
    private Double rstrReviewRating;

    @Column(name = "example", nullable = false)
    private boolean example;

    @Column(name = "relax", nullable = false)
    private boolean relax;

    @Column(name = "rstr_review_count", nullable = false)
    private int rstrReviewCount;

    @Column(name = "rstr_favorite_count", nullable = false)
    private int rstrFavoriteCount;

    @Column(name = "rstr_parking", nullable = false)
    private boolean rstrParking;

    @Column(name = "rstr_play", nullable = false)
    private boolean rstrPlay;

    @Column(name = "rstr_pet", nullable = false)
    private boolean rstrPet;

    @Column(name = "rstr_closed")
    private String rstrClosed;

    @Column(name = "rstr_business_hour")
    private String rstrBusinessHour;

    @Column(name = "rstr_delivery", nullable = false)
    private boolean rstrDelivery;

    @OneToMany(mappedBy = "rstrEntity")
    //@JsonManagedReference
    private List<RstrCategoryEntity> rstrCategories;

    @OneToMany(mappedBy = "rstrEntity")
    //@JsonManagedReference
    private List<RstrImgEntity> rstrImages;

    @OneToMany(mappedBy = "rstrEntity")
    //@JsonManagedReference
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "rstrEntity")
    //@JsonManagedReference
    private List<MenuDescriptionEntity> menuDescriptions;

    @ManyToMany(mappedBy = "rstrEntity")
    //@JsonBackReference
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "rstrEntity")
    //@JsonManagedReference
    private List<FavoriteEntity> favorites;
}
