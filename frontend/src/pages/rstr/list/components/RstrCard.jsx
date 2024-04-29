import React from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import StarIcon from '@mui/icons-material/Star';
import { useNavigate } from 'react-router-dom';

const RstrCardContainer = styled.div`
    margin: 5px;
    padding: 0.5rem;
    width: 170px;
    border-radius: 10px;
    border: 1px solid ${palette.lightblue};
    box-shadow: 2px 1px 2px ${palette.gray};
    // background-color: ${palette.lightblue};
    
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
        display: flex;
        align-items: center;
        font-size: 0.8rem;
    }
`;

const StyledStarIcon = styled(StarIcon)`
    color: ${palette.yellow};
    color: #FFD700;
`;

const RstrCard = () => {

    const navigate = useNavigate();

    return (
        <RstrCardContainer onClick={() => {
            navigate(`/rstr/${1}`)
        }}>
            <ImgContainer>
                <Img src={'/img/cat1.jpg'} alt={'이미지'} />
            </ImgContainer>
            <RstrInfo className='title'>
                {'금오식육식당'}
            </RstrInfo>
            <RstrInfo className='info'>
                <div>{'식육(숯불구이)'}</div>
                <div className='rating'>
                    <StyledStarIcon />
                    <div>
                        {`${4.9}(${10})`}
                    </div>
                </div>
            </RstrInfo>
        </RstrCardContainer>
    )
}

export default RstrCard;