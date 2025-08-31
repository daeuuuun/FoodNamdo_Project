package org.zerock.foodnamdo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"rstrEntity", "categoryEntity"})
@Table(name = "rstr_category")
public class RstrCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rstrCategoryId;

    @ManyToOne
    @JoinColumn(name = "rstr_id", nullable = false)
//    @JsonBackReference
    private RstrEntity rstrEntity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
//    @JsonBackReference
    private CategoryEntity categoryEntity;

}
