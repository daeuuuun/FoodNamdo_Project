import React, {useEffect, useState} from "react";
import styles from "./ReviewContain.module.css";
import { IoMdCheckmarkCircleOutline } from "react-icons/io";
import { PiClover } from "react-icons/pi";
import { IoHeartOutline } from "react-icons/io5";
import { MdHeartBroken } from "react-icons/md";
import {defaultBackInstance} from "../../../../utils/axiosInstance";
import ReviewCategory from "./ReviewCategory";

const ReviewContain = () => {
    const [userInfo, setUserInfo] = useState({});
    const rstr_id = 33445
    const getUserInfo = () => {
        defaultBackInstance.get(`mainsystem/RstrDetail?rstr_id=${rstr_id}`
        )
            .then((res) => {
                setUserInfo(res.data)
            })
            .catch(err => {console.log(err)})
    }

    useEffect(() => {
        getUserInfo()
    }, []);

    return (
        <>
            <div className={styles.reviewContainer}>
                <div className={styles.infoDiv}>
                    <div className={styles.nicknameText}>{userInfo.rstr_name}</div>
                    <div className={styles.badge}>
                        <IoMdCheckmarkCircleOutline className={styles.icons}/>
                        <PiClover className={styles.icons}/>
                    </div>
                    <div className={styles.nullDiv}></div>
                    <div className={styles.reviewCount}>[작성한 리뷰 n개]</div>
                    <div className={styles.heartReview}>
                        <IoHeartOutline className={styles.icons}/>
                        123
                        <MdHeartBroken className={styles.icons}/>
                        333
                    </div>
                </div>
                <ReviewCategory/>
                <div className={styles.reviewTextDiv}>
                    <div className={styles.textDiv}>
                        빵이 너무 너무 맛있어요 주차장은 따로 없지만 가게 앞에 주차는 잠깐은 가능하다고 하셨어요! 커피도 정말 잘 해요 아메리카노가 맛있는 집! 커피도 눈 앞에서 직접 내리고 빵
                        만드시는 곳도 볼 수는 있어서 청결은 말이 필요 없는 집! 사장님도 너무 친절하셔요! 하지만 가격이 조금 사악한 점은 어쩔 수 없나봐요ㅠㅠ 그래도 맛을 생각하면 가격이 이해가
                        됩니다 다들 여기 꼭꼭 오세요 제 인생 맛집.
                    </div>
                </div>
                <div className={styles.reviewTime}>2024.05.23.00:17:23</div>
            </div>
        </>
    )
};

export default ReviewContain;