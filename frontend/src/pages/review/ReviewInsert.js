import React, {useEffect, useState} from "react";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";
import ReviewContent from "./ReviewContent";
import {defaultBackInstance} from "../../utils/axiosInstance";

const ReviewInsert = () => {
    const [rating, setRating] = useState([])
    const [content, setContent] = useState('')

    const handleRatingChange = (ratingVal) => {
        setRating(ratingVal)
    }

    const handleContentChange = (contentVal) => {
        setContent(contentVal)
    }

    const handleSubmit = async () => {
        try {
            const response = await defaultBackInstance.post("/mainsystem/ReviewRegister", {
                rating, content
            })
            console.log(response.data)
        } catch (err) {
            console.log(err)
        }
    }

    return (
        <>
            <div className={styles.insertDiv}>
                <div className={styles.categoryDiv}>
                    <ReviewCategory onRatingChange={handleRatingChange}/>
                </div>
                <div className={styles.contentDiv}>
                    <ReviewContent onContentChange={handleContentChange}/>
                </div>
                <div className={styles.btnDiv}>
                    <div className={styles.nullDiv}></div>
                    <button className={styles.btn}>이미지 첨부</button>
                    <button className={styles.btn} onClick={handleSubmit}>리뷰 등록</button>
                </div>
            </div>
        </>
    )
};

export default ReviewInsert;