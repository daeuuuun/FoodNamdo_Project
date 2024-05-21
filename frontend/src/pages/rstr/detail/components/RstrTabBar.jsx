import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import RstrDetailInfo from './RstrDetailInfo';

const TabsContainer = styled.div`
    margin-top: 3rem;
    width: 100%;
`;

const TabsHeader = styled.div`
    display: flex;
    text-align: center;
`;

const Tab = styled.div`
    font-family: 'Gmarket Sans Medium';
    width: 50%;
    border-radius: 10px 10px 0 0;
    padding: 0.7rem;
    cursor: pointer;
    background-color: ${(props) => props.isActive ? `${palette.lightblue}` : `${palette.lightgray}`};
    color: ${(props) => props.isActive ? `black` : `${palette.darygray}`};
`;

const RstrTabBar = ({ rstrInfo }) => {
    const [activeTab, setActiveTab] = useState('상세정보');
    const [menuItemList, setMenuItemList] = useState([]);

    useEffect(() => {
        setMenuItemList([
            {
                title: '상세정보',
                component: <RstrDetailInfo rstrInfo={rstrInfo} />
            },
            {
                title: '리뷰',
                component: <RstrDetailInfo rstrInfo={rstrInfo} />
            },
        ]);
    }, [rstrInfo]);

    return (
        <TabsContainer>
            <TabsHeader>
                {
                    menuItemList.map((item) => (
                        <Tab
                            key={item.title}
                            isActive={item.title === activeTab}
                            onClick={() => setActiveTab(item.title)}
                        >
                            {item.title}
                        </Tab>
                    ))
                }
            </TabsHeader>
            {menuItemList.find(item => item.title === activeTab)?.component}
        </TabsContainer>
    )
}

export default RstrTabBar;