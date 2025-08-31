package org.zerock.foodnamdo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name",nullable = false, length = 10)
    private String categoryName;

//    @OneToMany(mappedBy = "category")
//    private Set<RstrCategoryEntity> restaurantCategories = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "rstr_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "rstr_id")
    )
//    @JsonBackReference
    private List<RstrEntity> rstrEntity;

}
