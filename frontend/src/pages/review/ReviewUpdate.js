import React, { useEffect, useState, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";
import ReviewContent from "./ReviewContent";
import { authBackInstance } from "../../utils/axiosInstance";

const ReviewUpdate = () => {
    const { search } = useLocation();
    const navigate = useNavigate();
    const params = new URLSearchParams(search);
    const reviewId = params.get('review_id');

    const [rating, setRating] = useState([
        { id: 1, title: '맛', rating: 0 },
        { id: 2, title: '가격', rating: 0 },
        { id: 3, title: '청결', rating: 0 },
        { id: 4, title: '친절', rating: 0 },
        { id: 5, title: '편의시설', rating: 0 }
    ]);
    const [content, setContent] = useState('');

    useEffect(() => {
        const fetchReview = async () => {
            try {
                const response = await authBackInstance.get("/mypage/getReview");
                const review = response.data.find(r => r.review_id === parseInt(reviewId));
                if (review) {
                    setContent(review.review_text);
                    setRating([
                        { id: 1, title: '맛', rating: review.category_rating_taste },
                        { id: 2, title: '가격', rating: review.category_rating_price },
                        { id: 3, title: '청결', rating: review.category_rating_clean },
                        { id: 4, title: '친절', rating: review.category_rating_service },
                        { id: 5, title: '편의시설', rating: review.category_rating_amenities }
                    ]);
                } else {
                    console.error('Review not found');
                }
            } catch (err) {
                console.error('Fetch review error:', err);
            }
        };

        fetchReview();
    }, [reviewId]);

    const handleRatingChange = (ratingVal) => {
        setRating(ratingVal);
    };

    const handleContentChange = (contentVal) => {
        setContent(contentVal);
    };

    const handleSubmit = async () => {
        try {
            await authBackInstance.post("/mainsystem/modifyReview", {
                review_id: reviewId,
                review_text: content,
                category_rating_taste: rating.find(r => r.id === 1)?.rating,
                category_rating_price: rating.find(r => r.id === 2)?.rating,
                category_rating_clean: rating.find(r => r.id === 3)?.rating,
                category_rating_service: rating.find(r => r.id === 4)?.rating,
                category_rating_amenities: rating.find(r => r.id === 5)?.rating
            });
            console.log('Review updated successfully');
            navigate('/mypage');
        } catch (err) {
            console.error('Submit review error:', err);
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
                console.log('Image uploaded successfully:', response);
            } catch (error) {
                console.error('Image upload error:', error);
            }
        }
    };

    return (
        <div className={styles.insertDiv}>
            <div className={styles.categoryDiv}>
                <ReviewCategory onRatingChange={handleRatingChange} initialRatings={rating} />
            </div>
            <div className={styles.contentDiv}>
                <ReviewContent onContentChange={handleContentChange} initialContent={content} />
            </div>
            <div className={styles.btnDiv}>
                <button className={styles.btn} onClick={handleButtonClick}>이미지 첨부</button>
                <input
                    type="file"
                    ref={fileInputRef}
                    style={{ display: 'none' }}
                    onChange={handleFileChange}
                />
                <button className={styles.btn} onClick={handleSubmit}>리뷰 수정</button>
            </div>
        </div>
    );
};

export default ReviewUpdate;
