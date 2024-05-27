import React, { useState } from "react";
import styles from "./ReviewCategory.module.css";
import { FaRegStar, FaStar } from "react-icons/fa";

const ReviewCategoryDetail = ({ items, onRatingChange }) => {
    const [rating, setRating] = useState(0);
    const [showOptions, setShowOptions] = useState(false);
    const ratings = [0, 1, 2, 3, 4, 5];

    const handleRatingClick = (value) => {
        setRating(value);
        setShowOptions(false);
        onRatingChange(items.id, value); // 부모 컴포넌트로 id와 선택된 별점 전달
    };

    return (
        <div className={styles.tasteDiv}>
            <div className={styles.textDiv}>{items.title}</div>
            <div className={styles.ratingDiv}>
                <button className={styles.ratingButton} onClick={() => setShowOptions(!showOptions)}>
                    {rating === null ? "별점" : rating}
                </button>
                {showOptions && (
                    <div className={styles.ratingOptions}>
                        {ratings.map((value) => (
                            <button
                                key={value}
                                className={styles.ratingOption}
                                onClick={() => handleRatingClick(value)}
                            >
                                {value}
                            </button>
                        ))}
                    </div>
                )}
                {rating !== null && (
                    <div className={styles.iconDiv}>
                        {[1, 2, 3, 4, 5].map((value) => (
                            value <= rating ? <FaStar key={value} className={styles.icons} /> : <FaRegStar key={value} className={styles.icons} />
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default ReviewCategoryDetail;
