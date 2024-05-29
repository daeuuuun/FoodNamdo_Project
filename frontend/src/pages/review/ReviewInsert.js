import React, {useEffect, useState} from "react";
import { useParams } from "react-router-dom";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";
import ReviewContent from "./ReviewContent";
import { authBackInstance } from "../../utils/axiosInstance";

const ReviewInsert = () => {
    const { id } = useParams();

    const [rating, setRating] = useState([]);
    const [content, setContent] = useState('');

    const handleRatingChange = (ratingVal) => {
        setRating(ratingVal);
    };

    const handleContentChange = (contentVal) => {
        setContent(contentVal);
    };

    const handleSubmit = async () => {
        try {
            const response = await authBackInstance.post("/mainsystem/ReviewRegister", {
                rstr_id: id,
                review_text: content,
                time_of_creation: new Date().toISOString().slice(0, -1),
                category_rating_taste: rating[0]?.rating,
                category_rating_price: rating[1]?.rating,
                category_rating_clean: rating[2]?.rating,
                category_rating_service: rating[3]?.rating,
                category_rating_amenities: rating[4]?.rating
            }).then(response => console.log(response)).catch(err => console.log(err));
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div className={styles.insertDiv}>
            <div className={styles.categoryDiv}>
                <ReviewCategory onRatingChange={handleRatingChange} />
            </div>
            <div className={styles.contentDiv}>
                <ReviewContent onContentChange={handleContentChange} />
            </div>
            <div className={styles.btnDiv}>
                <button className={styles.btn}>이미지 첨부</button>
                <button className={styles.btn} onClick={handleSubmit}>리뷰 등록</button>
            </div>
        </div>
    );
};

export default ReviewInsert;
