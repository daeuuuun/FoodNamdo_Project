import React from 'react';
import RstrTabBar from './components/RstrTabBar';
import UpArrow from './../../../components/common/UpArrow';
import styled from 'styled-components';
import RstrImgCarusel from './components/RstrImgCarusel';
import RstrBriefInfo from './components/RstrBriefInfo';
import { useLocation } from 'react-router-dom';

const RstrDetailMainContainer = styled.div`
    padding-top: 50px;
`;

const RstrDetailPage = () => {

    const location = useLocation();
    const { rstrInfo } = location.state;

    return (
        <div style={{ padding: '0 3rem' }}>
            <RstrDetailMainContainer>
                <div style={{ margin: '5px 50px', fontSize: '0.9rem' }}>
                    <span>{rstrInfo.rstr_region}</span>
                    <span> | </span>
                    <span>{rstrInfo.category_name}</span>
                </div>
                <div style={{ display: 'flex' }}>
                    <RstrImgCarusel />
                    <RstrBriefInfo rstrInfo={rstrInfo} />
                </div>
            </RstrDetailMainContainer>
            <RstrTabBar />
        </div>
    )
}

export default RstrDetailPage;