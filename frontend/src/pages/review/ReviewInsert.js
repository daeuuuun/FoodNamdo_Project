import React from "react";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";
import ReviewContent from "./ReviewContent";
import {defaultBackInstance} from "../../utils/axiosInstance";

const ReviewInsert = () => {
    return (
        <>
            <div className={styles.insertDiv}>
                <div className={styles.categoryDiv}>
                    <ReviewCategory/>
                </div>
                <div className={styles.contentDiv}>
                    <ReviewContent />
                </div>
                <div className={styles.btnDiv}>
                    <div className={styles.nullDiv}></div>
                    <button className={styles.btn}>이미지 첨부</button>
                    <button className={styles.btn}>리뷰 등록</button>
                </div>
            </div>
        </>
    )
};

export default ReviewInsert;