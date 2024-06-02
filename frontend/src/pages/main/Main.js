import React, {useContext, useEffect, useState} from 'react';
import MainTopCategory from './MainTopCategory';
import MainBottomButton from './MainBottomButton';
import styles from '../main/Main.module.css';
import CarouselContainer from './CarouselContainer';
import cafe1 from '../main/cafe1.jpg';
import cafe2 from '../main/cafe2.jpg';
import cafe3 from '../main/cafe3.jpg';
import style1 from '../main/Recommend.module.css';
import style2 from '../main/Best.module.css';
import {AppContext} from "../../utils/loginContext";
import {Link} from "react-router-dom";
import {defaultImageInstance} from "../../utils/axiosInstance";



const Main = () => {

    const { isAuthenticated } = useContext(AppContext);

    const userId = 17;
    const [recommended, setRecommend] = useState({
        category: [{
            rstr_img_url: [cafe1],
            rstr_name: 'cafe1'
        }, {
            rstr_img_url: [cafe2],
            rstr_name: 'cafe2'
        }, {
            rstr_img_url: [cafe3],
            rstr_name: 'cafe3'
        }]

    });

    useEffect(() => {
        const recom = async () => {
            await defaultImageInstance.get(`/recommend/${userId}`).then(res => {setRecommend(res.data); console.log(res.data)}).catch(err => console.log(err))
        }
        if(isAuthenticated) {
            recom()
        }

        }, [])

    return (
        <>
            <div className={styles.backgroundColor}>
                {isAuthenticated ? (<><CarouselContainer title={'#나에게 딱 맞는 맛집'} styles={style1} images={recommended.category}/>
                </>) : (<>
                    <Link className={styles.loginLink} to={"/login"}>로그인 후 추천시스템을 이용할 수 있습니다!</Link>
                </>)}
                <div className={styles.div}>
                <div className={styles.div2}>
                    {<CarouselContainer title={'#BEST 맛집'} styles={style2} images={recommended.category} onesImages={2} auto={false}/>}
                    {<CarouselContainer title={'#BEST REVIEW'} styles={style2} images={recommended.category} onesImages={2} auto={false}/>}
                </div>
                <MainBottomButton/>
                </div>
            </div>
        </>

    )
}

export default Main;
