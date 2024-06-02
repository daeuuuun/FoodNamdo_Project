import React, {useEffect, useState} from 'react';
import styles from '../myPage/MyReview.module.css';
import {authBackInstance} from "../../utils/axiosInstance";
import Review from "../rstr/detail/components/Review";
import {Link} from "react-router-dom";

const ReviewContain = () => {

  const [myReviews, setMyReviews] = useState([]);

  useEffect(() => {
    authBackInstance.get("/mypage/getReview")
        .then(res => {setMyReviews(res.data); console.log(res.data)}).catch(err => console.log(err))
  }, []);

  const removeFoodReview = review_id => {
    authBackInstance.post(`/mainsystem/deleteReview?review_id=${review_id}`).then(res => console.log(res)).catch(err => console.log(err))
  }

  return (
    <>
      <div className={styles.Contain}>
        <div className={styles.topBar}>

        </div>
        {myReviews.map(review => {
          return <>
            <div className={styles.topdiv}>
              <div className={styles.storeName}>{review.rstrName}</div>
              <div className={styles.btns}><Link to={"/"} >수정</Link></div>
              <div className={styles.btns} onClick={() => removeFoodReview(review.review_id)}>삭제</div>
            </div>
            <Review key={review.review_id} item={review}/>
          </>
        })}
      </div>
    </>
  );
};

export default ReviewContain;