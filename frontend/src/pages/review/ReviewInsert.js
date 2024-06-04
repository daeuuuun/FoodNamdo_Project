import React, {useRef, useState, useEffect, useContext} from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";
import ReviewContent from "./ReviewContent";
import { authBackInstance } from "../../utils/axiosInstance";
import {AppContext} from "../../utils/loginContext";

const ReviewInsert = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [rating, setRating] = useState([]);
    const [content, setContent] = useState('');
    const [reviewId, setReviewId] = useState(null);

    const { isAuthenticated } = useContext(AppContext);

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('/login')
        }
    }, []);

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
            });
            setReviewId(response.data);
            console.log(response.data)
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
            formData.append('review_id', reviewId);

            try {
                const response = await authBackInstance.post(`/mainsystem/uploadReviewImage?review_id=${reviewId}`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                console.log(response);
                navigate(-1);
            } catch (error) {
                console.log(error);
            }
        } else {
            console.log("Review ID is not set or file is not selected.");
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
