import React from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import AdditionInfo from './AdditionInfo';
import RstrMap from './RstrMap';

const RstrDetailInfoContainer = styled.div`
    padding: 10px;
`;

const InfoContainer = styled.div`
    margin: 2.5rem 0;
`;

const InfoTitle = styled.div`
    font-family: 'Gmarket Sans Medium';
    font-size: 1.5rem;
    color: ${palette.darkblue2}
`;

const InfoContent = styled.div`
    margin-top: 0.8rem;
    font-size: 0.9rem;
`;

const RstrDetailInfo = () => {
    return (
        <RstrDetailInfoContainer>
            <InfoContainer>
                <InfoTitle>소개내용</InfoTitle>
                <InfoContent>OO식당은 한식을 전문으로 하는 매장입니다. 경상남도 OO시에 위치해 있습니다.</InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>운영시간</InfoTitle>
                <InfoContent>
                    <div>매일 10:30 ~ 20:30</div>
                    <div>휴무일 - 화요일</div>
                </InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>메뉴정보</InfoTitle>
                <InfoContent></InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>추가정보</InfoTitle>
                <InfoContent>
                    <AdditionInfo />
                </InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>음식점 위치</InfoTitle>
                <InfoContent>
                    <div>경상남도 사천시 각산로 54</div>
                    <RstrMap />
                </InfoContent>
            </InfoContainer>
        </RstrDetailInfoContainer>
    )
}

export default RstrDetailInfo;