import React from "react";
import styles from "./ReviewCategory.module.css";
import { FaRegStar } from "react-icons/fa";
import { FaStar } from "react-icons/fa";

const ReviewCategoryDetail = ({ title }) => {
    return (
        <>
            <div className={styles.tasteDiv}>
                <div className={styles.textDiv}>{title}</div>
                <div className={styles.ratingDiv}>
                    <div className={styles.iconDiv}>
                        <FaStar className={styles.icons}/>
                        <FaStar className={styles.icons}/>
                        <FaStar className={styles.icons}/>
                        <FaRegStar className={styles.icons}/>
                        <FaRegStar className={styles.icons}/>
                    </div>
                </div>
            </div>
        </>
    )
};

export default ReviewCategoryDetail;