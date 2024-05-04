import React from 'react';
import RstrTabBar from './components/RstrTabBar';
import UpArrow from './../../../components/common/UpArrow';
import styled from 'styled-components';
import RstrImgCarusel from './components/RstrImgCarusel';
import RstrBriefInfo from './components/RstrBriefInfo';

const RstrDetailMainContainer = styled.div`
    padding-top: 50px;
`;

const RstrDetailPage = () => {


    return (
        <div style={{ padding: '0 3rem' }}>
            <RstrDetailMainContainer>
                <div style={{ margin: '5px 50px', fontSize: '0.9rem' }}>
                    <span>경상남도</span>
                    <span> | </span>
                    <span>한식</span>
                </div>
                <div style={{ display: 'flex' }}>
                    <RstrImgCarusel />
                    <RstrBriefInfo />
                </div>
            </RstrDetailMainContainer>
            <RstrTabBar />
        </div>
    )
}

export default RstrDetailPage;