import React, { useState, useEffect, useCallback } from 'react';
import noImage from '../../asset/noimage.jpg'

const CarouselContainer = ({ title, styles, images, auto, time, onesImages }) => {
    console.log(images)
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const handleRightButton = useCallback(() => {
        setCurrentImageIndex(prevIndex =>
            prevIndex === images.length - 1 ? 0 : prevIndex + 1
        )
    }, [])
    const handleLeftButton = useCallback(() => {
        setCurrentImageIndex(prevIndex =>
            prevIndex === 0 ? images.length - 1 : prevIndex - 1
        )
    }, [])
    useEffect(() => {
        let intervalId;
        if (auto) {
            intervalId = setInterval(() => {
                setCurrentImageIndex(prevIndex =>
                    prevIndex === images.length - 1 ? 0 : prevIndex + 1
                );
            }, time || 3000);
        }

        return () => {
            if (intervalId) clearInterval(intervalId);
        };
    }, []);

    return (
        <>
            <div className={styles.recommendContain}>
                <div className={styles.recommendText}>{title}</div>
                <div className={styles.recImgContain}>
                    {!auto && <button className={styles.buttonStyle} onClick={handleLeftButton}>&lt;</button>}
                    {Array.from({ length: onesImages}).map((_, index) => {
                        const newIndex = (currentImageIndex + index) % images.length;
                        return (
                            <div key={newIndex} className={styles.recImgDiv}>
                                {images[newIndex].rstr_img_url[0] ? (<img src={images[newIndex].rstr_img_url[0]} className={styles.recommendStoreImg} alt={`Cafe ${newIndex + 1}`} />) : (
                                    <img src={noImage} className={styles.recommendStoreImg}
                                         alt={`Cafe ${newIndex + 1}`}/>)}
                                <div className={styles.imgText}>{images[newIndex].rstr_name}</div>
                            </div>
                        );
                    })}
                    {!auto && <button className={styles.buttonStyle} onClick={handleRightButton}>&gt;</button>}
                </div>
            </div>
        </>
    );
};

CarouselContainer.defaultProps = {
    auto: true,
    time: 3000,
    onesImages: 3
};

export default CarouselContainer;
