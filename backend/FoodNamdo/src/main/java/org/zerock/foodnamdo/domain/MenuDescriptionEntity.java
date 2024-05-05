package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu_description")
public class MenuDescriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_description_id")
    private Long menuDescriptionId;

    @ManyToOne
    @JoinColumn(name = "rstr_id")
    private RstrEntity restaurant;

    @JoinColumn(name = "menu_id")
    private int menuId;

    @Column(name = "menu_category_sub", nullable = false)
    private String menuCategorySub;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private int menuPrice;
}
