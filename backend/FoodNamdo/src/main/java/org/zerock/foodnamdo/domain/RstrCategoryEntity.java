package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "rstr_category")
public class RstrCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rstr_category_id;

    @ManyToOne
    @JoinColumn(name = "rstr_id", nullable = false)
    private RstrEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryId;
}
