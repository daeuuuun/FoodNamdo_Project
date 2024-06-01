import React, {useRef, useState} from "react";
import { useParams } from "react-router-dom";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";
import ReviewContent from "./ReviewContent";
import { authBackInstance } from "../../utils/axiosInstance";

const ReviewInsert = () => {
    const { id } = useParams();

    const [rating, setRating] = useState([]);
    const [content, setContent] = useState('');
    const [reviewId, setReviewId] = useState(0);

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
            }).then(res => setReviewId(res.data)).catch(err => console.log(err));
        } catch (err) {
            console.log(err);
        }
    };

    const fileInputRef = useRef(null);

    const handleButtonClick = () => {
        fileInputRef.current.click();
    };

    const handleFileChange = async (event) => {
        const file = event.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('file', file);
            formData.append('review_id', id); // review_id 추가

            try {
                const response = await authBackInstance.post(`/mainsystem/uploadReviewImage?review_id=${reviewId}`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                }).then(res => console.log(res)).catch(err => console.log(err))

            } catch (error) {

            }
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
                <button className={styles.btn} onClick={handleButtonClick}>이미지 첨부</button>
                <input
                    type="file"
                    ref={fileInputRef}
                    style={{display: 'none'}}
                    onChange={handleFileChange}
                />
                <button className={styles.btn} onClick={handleSubmit}>리뷰 등록</button>
            </div>
        </div>
    );
};

export default ReviewInsert;
