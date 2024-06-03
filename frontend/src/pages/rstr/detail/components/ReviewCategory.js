import React, { useEffect, useState } from "react";
import ReviewCategoryDetail from "./ReviewCategoryDetail";
import styles from "./ReviewCategory.module.css";

const ReviewCategory = ({ onRatingChange, initialRatings = [] }) => { // 기본값 설정
    const categoryTitle = [
        { id: 1, title: '맛', rating: 0 },
        { id: 2, title: '가격', rating: 0 },
        { id: 3, title: '청결', rating: 0 },
        { id: 4, title: '친절', rating: 0 },
        { id: 5, title: '편의시설', rating: 0 }
    ];

    const [categoryList, setCategoryList] = useState(categoryTitle);

    useEffect(() => {
        if (initialRatings.length) {
            setCategoryList(initialRatings);
        }
    }, [initialRatings]);

    const handleRatingChange = (id, rating) => {
        const updatedCategoryList = categoryList.map(category => {
            if (category.id === id) {
                return { ...category, rating };
            }
            return category;
        });
        setCategoryList(updatedCategoryList);
        onRatingChange(updatedCategoryList);
    };

    return (
        <div className={styles.container}>
            {categoryList.map(category => (
                <ReviewCategoryDetail
                    key={category.id}
                    items={category}
                    onRatingChange={handleRatingChange}
                />
            ))}
        </div>
    );
};

export default ReviewCategory;
