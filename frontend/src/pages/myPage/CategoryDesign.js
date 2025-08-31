import React from 'react';
import styles from '../myPage/MyReview.module.css';
import { FaRegStar, FaStar } from "react-icons/fa";

const CategoryDesign = () => {
  return (
    <>
    <div className={styles.categoryDiv}>
          <div className={styles.categoryName}>맛</div>
          <div className={styles.categoryRating}>
            <FaStar/>
            <FaStar/>
            <FaStar/>
            <FaRegStar/>
            <FaRegStar/>
          </div>
        </div>
    </>
  );
};

export default CategoryDesign;