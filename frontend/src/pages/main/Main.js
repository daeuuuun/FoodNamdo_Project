import React, {useContext} from 'react';
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



const Main = () => {

    const { isAuthenticated } = useContext(AppContext);

    const images = [{
        imgSrc: cafe1,
        name: 'cafe1'
    }, {
        imgSrc: cafe2,
        name: 'cafe2'
    }, {
        imgSrc: cafe3,
        name: 'cafe3'
    }];

    return (
        <>
            <div className={styles.backgroundColor}>
                {/* <MainTopCategory/> */}
                {isAuthenticated ? (<><CarouselContainer title={'#나에게 딱 맞는 맛집'} styles={style1} images={images}/>




                </>) : (<Link to={"/login"}>로그인이 필요합니다!!</Link>)}
                <div className={styles.div}>
                <div className={styles.div2}>
                    <CarouselContainer title={'#BEST 맛집'} styles={style2} images={images} onesImages={2} auto={false}/>
                    <CarouselContainer title={'#BEST REVIEW'} styles={style2} images={images} onesImages={2} auto={false}/>
                </div>
                <MainBottomButton/>
                </div>
            </div>
        </>

    )
}

export default Main;
