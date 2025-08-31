import React, {useState} from 'react';
import styles from "./ReviewCategory.module.css";
import {FaRegStar, FaStar} from "react-icons/fa";

const ReviewRating = ({title, rating}) => {
    const [star, setStar] = useState(rating);
    const [showOptions, setShowOptions] = useState(true);
    const ratings = [0, 1, 2, 3, 4, 5];



    return (
        <>
        <div className={styles.tasteDiv}>
            <div className={styles.textDiv}>{title}</div>
            <div className={styles.ratingDiv}>

                {rating !== null && (
                    <div className={styles.iconDiv}>
                        {[1, 2, 3, 4, 5].map((value) => (
                            value <= star ? <FaStar key={value} className={styles.icons} /> : <FaRegStar key={value} className={styles.icons} />
                        ))}
                    </div>
                )}
            </div>
        </div>
        </>
    );
};

export default ReviewRating;