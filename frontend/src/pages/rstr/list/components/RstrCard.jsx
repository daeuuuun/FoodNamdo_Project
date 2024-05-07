import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import StarRoundedIcon from '@mui/icons-material/StarRounded';
import { useNavigate } from 'react-router-dom';

const RstrCardContainer = styled.div`
    margin: 5px 8px;
    padding: 0.5rem;
    width: 167px;
    border-radius: 10px;
    border: 1px solid ${palette.lightblue};
    box-shadow: 2px 1px 2px ${palette.gray};
    background-color: ${palette.lightblue};
    
    &:hover {
        cursor: pointer;
    }
`
const ImgContainer = styled.div`
    width: 100%;
    height: 10rem;
    overflow: hidden;
    border-radius: 10px;
`;

const Img = styled.img`
    width: 100%;
    height: 100%;
    object-fit: cover;
`;

const RstrInfo = styled.div`
    display: flex;
    align-items: center;
    &.title {    
        padding: 8px 0 0 0;
        font-family: 'Gmarket Sans Bold';
        justify-content: center;
        font-size: 1.1rem;
    }

    &.info {
        justify-content: space-between;
        display: flex;
        font-size: 0.8rem;
    }

    &.info .rating {
        flex-direction: row;
        font-size: 0.8rem;
    }
`;

const StyledStarIcon = styled(StarRoundedIcon)`
    color: ${palette.yellow};
    color: #FFD700;
    margin-right: -2px;
`;

const RstrCard = ({ rstrInfo }) => {

    const navigate = useNavigate();
    const imgUrl = rstrInfo.rstr_img_url;

    return (
        <RstrCardContainer onClick={() => {
            navigate(`/rstr/${rstrInfo.rstr_id}`, { state: { rstrInfo } })
        }}>
            <ImgContainer>
                <Img src={imgUrl} alt={'이미지'} />
            </ImgContainer>
            <RstrInfo className='title'>
                {rstrInfo.rstr_name}
            </RstrInfo>
            <RstrInfo className='info'>
                <div style={{ marginTop: '1px' }}>
                    <span>{rstrInfo.rstr_region}</span>
                    <span> | </span>
                    <span>{rstrInfo.category_name}</span>
                </div>
                <div className='centered-flex rating'>
                    <StyledStarIcon />
                    <div style={{ marginTop: '1px' }}>
                        {`${rstrInfo.rstr_review_rating} (${rstrInfo.review_count}명)`}
                    </div>
                </div>
            </RstrInfo>
        </RstrCardContainer>
    )
}

export default RstrCard;