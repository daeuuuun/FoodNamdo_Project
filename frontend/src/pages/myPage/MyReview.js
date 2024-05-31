import React from "react";
import styles from '../myPage/MyReview.module.css';
import ReviewContain from "./ReviewContain";

const MyReview = () => {
    return (
        <>
            <div className={styles.background}>
                <div className={styles.rightContainer}>
                    <ReviewContain/>
                    <ReviewContain/>
                    <ReviewContain/>
                    <ReviewContain/>
                    <ReviewContain/>
                </div>
            </div>
        </>
    )
}

export default MyReview;