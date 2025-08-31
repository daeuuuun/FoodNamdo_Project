import React, { useEffect, useState } from "react";
import ReviewCategoryDetail from "./ReviewCategoryDetail"; // 필요한 경우 import
import styles from "./ReviewCategory.module.css";
import ReviewRating from "./ReviewRating";

const ReviewCheck = ({ rstrInfo }) => {

    return (
        <div className={styles.container}>
            {<ReviewRating title = {'맛'} rating = {rstrInfo.category_rating_taste}/>}
            {<ReviewRating title = {'가격'} rating = {rstrInfo.category_rating_price}/>}
            {<ReviewRating title = {'청결'} rating = {rstrInfo.category_rating_clean}/>}
            {<ReviewRating title = {'친절'} rating = {rstrInfo.category_rating_service}/>}
            {<ReviewRating title = {'편의시설'} rating = {rstrInfo.category_rating_amenities}/>}
        </div>
    );
};

export default ReviewCheck;
