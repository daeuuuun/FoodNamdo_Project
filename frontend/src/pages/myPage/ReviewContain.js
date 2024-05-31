import React from 'react';
import styles from '../myPage/MyReview.module.css';
import CategoryDesign from './CategoryDesign';

const ReviewContain = () => {
  return (
    <>
      <div className={styles.Contain}>
        <div className={styles.topBar}>
        <div className={styles.storeName}>음식점 이름</div>
          <div className={styles.btns}>수정</div>
          <div className={styles.btns}>삭제</div>
        </div>
        <div className={styles.categorys}>
          <CategoryDesign/>
          <CategoryDesign/>
          <CategoryDesign/>
          <CategoryDesign/>
          <CategoryDesign/>
        </div>
        <div className={styles.textContain}>
          리뷰테스트인데 뭐라고 써야할지 모르겠다리뷰테스트인데 뭐라고 써야할지 모르겠다리뷰테스트인데 뭐라고 써야할지 모르겠다리뷰테스트인데 뭐라고 써야할지 모르겠다리뷰테스트인데 뭐라고 써야할지 모르겠다리뷰테스트인데 뭐라고 써야할지 모르겠다리뷰테스트인데 뭐라고 써야할지 모르겠다리뷰테스트인데 뭐라고 써야할지 모르겠다 
        </div>
        <div className={styles.reviewTime}>
          2024.05.31.12:11:45
        </div>
      </div>
    </>
  );
};

export default ReviewContain;