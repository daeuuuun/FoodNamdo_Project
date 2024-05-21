import React, { useEffect, useState } from 'react';
import RstrTabBar from './components/RstrTabBar';
import styled from 'styled-components';
import RstrImgCarusel from './components/RstrImgCarusel';
import RstrBriefInfo from './components/RstrBriefInfo';
import { useParams } from 'react-router-dom';
import { BACKEND_SERVER_URL } from '../../../config/Config';
import axios from 'axios';

const RstrDetailMainContainer = styled.div`
    padding-top: 50px;
`;

const RstrDetailPage = () => {

    const [rstrDetails, setRstrDetails] = useState(null);
    const { rstrId } = useParams();

    useEffect(() => {
        const fetchRstrDetails = async () => {
            try {
                const response = await axios.get
                    (BACKEND_SERVER_URL + '/mainsystem/RstrDetail', {
                        params: {
                            rstr_id: rstrId
                        }
                    });
                setRstrDetails(response.data);
            } catch (error) {
                console.error('음식점 상세 정보를 가져오는데 실패했습니다.', error);
            }
        };

        fetchRstrDetails();
    }, [rstrId]);

    if (!rstrDetails) {
        return <div></div>;
    }

    return (
        <div style={{ padding: '0 3rem' }}>
            <RstrDetailMainContainer>
                <div style={{ margin: '5px 50px', fontSize: '0.9rem' }}>
                    <span>{rstrDetails.rstr_region}</span>
                    <span> | </span>
                    <span>{rstrDetails.category_name}</span>
                </div>
                <div
                    style={{ display: 'flex' }}>
                    <RstrImgCarusel rstrImg={rstrDetails.rstr_images} />
                    <RstrBriefInfo rstrInfo={rstrDetails} />
                </div>
            </RstrDetailMainContainer >
            <RstrTabBar rstrInfo={rstrDetails} />
        </div >
    )
}

export default RstrDetailPage;