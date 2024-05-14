package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"rstrEntity", "userEntity"})
@Table(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "rstr_id")
//    private Long rstrId;
    private RstrEntity rstrEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Lob
    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_of_creation", nullable = false)
    private LocalDateTime timeOfCreation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_of_revision")
    private LocalDateTime timeOfRevision;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "category_rating_taste", nullable = false)
    private double categoryRatingTaste;

    @Column(name = "category_rating_price", nullable = false)
    private double categoryRatingPrice;

    @Column(name = "category_rating_clean", nullable = false)
    private double categoryRatingClean;

    @Column(name = "category_rating_service", nullable = false)
    private double categoryRatingService;

    @Column(name = "category_rating_amenities", nullable = false)
    private double categoryRatingAmenities;

    @Column(name = "receipt", nullable = false)
    private boolean receipt;

    @Column(name = "like", nullable = false)
    private int like = 0;

    @Column(name = "dislike", nullable = false)
    private int dislike = 0;

    @OneToMany(mappedBy = "reviewEntity")
    private List<ReviewImgEntity> reviewImages;

    // Getters and setters
}
