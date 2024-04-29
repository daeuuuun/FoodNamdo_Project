import React from "react";
import styled from 'styled-components';
import Slider from "react-slick";
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const Container = styled.div`
  width: 35%;
  padding: 0 2rem;
  position: relative;
`;

const StyledSlider = styled(Slider)`
    .slick-slide div {
      outline: none;
    }

    .slick-prev,
    .slick-next {
        background-color: #254871;
        border-radius: 50%;
        border: none;
        height: 1.7rem;
        width: 1.7rem;
        cursor: pointer;
        transition: background-color 0.3s ease;
        display: flex;
        justify-content: center;
        align-items: center;
        color: white;
    }

    .slick-prev:hover,
    .slick-next:hover {
        background-color: #112D4E;
    }
`;

const ImageWrapper = styled.div`
    margin: 0 auto;
    max-width: 90%;
    height: auto;

    width: 100%;
    height: 16rem;
    overflow: hidden;
    border-radius: 20px;
`;

const Img = styled.img`
    width: 100%;
    height: 100%;
    object-fit: cover;
`;

const rstrImageInfo = [
    {
        id: 1,
        src: require('../img/cat1.jpg'),
        name: '첫번째 고양이'
    },
    {
        id: 2,
        src: require('../img/cat2.jpg'),
        name: '두번째 고양이'
    },
    {
        id: 3,
        src: require('../img/cat3.jpg'),
        name: '세번째 고양이'
    }
];

const RstrImgCarusel = () => {
    const settings = {
        dots: true,
        fade: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        waitForAnimate: false,
    };
    return (
        <Container>
            <StyledSlider {...settings}>
                {rstrImageInfo.map(info => (
                    <div key={info.id}>
                        <ImageWrapper>
                            <Img src={info.src} alt={info.name} />
                        </ImageWrapper>
                    </div>
                ))}
            </StyledSlider>
        </Container>
    );
}

export default RstrImgCarusel;
