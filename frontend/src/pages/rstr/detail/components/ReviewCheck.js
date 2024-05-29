import React, {useEffect, useState} from "react";
import ReviewCategoryDetail from "./ReviewCategoryDetail";
import styles from "./ReviewCategory.module.css";

const ReviewCheck = ({ rstrInfo }) => {
    console.log(rstrInfo.reviews)

    const categoryTitle = [
        { id: 1, title: '맛', rating: rstrInfo.reviews.category_rating_taste },
        { id: 2, title: '가격', rating: rstrInfo.reviews.category_rating_price },
        { id: 3, title: '청결', rating: rstrInfo.reviews.category_rating_clean },
        { id: 4, title: '친절', rating: rstrInfo.reviews.category_rating_service },
        { id: 5, title: '편의시설', rating: rstrInfo.reviews.category_rating_amenities }
    ];

    const [categoryList, setCategoryList] = useState();

    return (
        <div className={styles.container}>
            {categoryList.map(category => (
                <ReviewCategoryDetail
                    key={category.id}
                    items={category}
                />
            ))}
        </div>
    );
};

export default ReviewCheck;
