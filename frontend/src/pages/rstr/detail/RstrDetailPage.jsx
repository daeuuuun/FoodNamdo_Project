import React, { useEffect, useState, useContext } from 'react';
import RstrTabBar from './components/RstrTabBar';
import styled from 'styled-components';
import RstrImgCarusel from './components/RstrImgCarusel';
import RstrBriefInfo from './components/RstrBriefInfo';
import { useParams } from 'react-router-dom';
import { defaultBackInstance, authBackInstance } from "../../../utils/axiosInstance";
import { AppContext } from '../../../utils/loginContext'

const RstrDetailMainContainer = styled.div`
    padding-top: 50px;
`;

const RstrDetailPage = () => {

    const { isAuthenticated } = useContext(AppContext);
    const [rstrDetails, setRstrDetails] = useState(null);
    const { rstrId } = useParams();

    useEffect(() => {
        const fetchRstrDetails = async () => {
            try {
                const response = await authBackInstance.get
                    ('/mainsystem/RstrDetail', {
                        params: {
                            rstr_id: rstrId
                        }
                    });
                setRstrDetails(response.data);
            } catch (error) {
                console.error('음식점 상세 정보를 가져오는데 실패했습니다.', error);
            }
        };

        const fetchTokenRstrDetails = async () => {
            try {
                const response = await defaultBackInstance.get
                    ('/mainsystem/RstrDetailNoToken', {
                        params: {
                            rstr_id: rstrId
                        }
                    });
                setRstrDetails(response.data);
            } catch (error) {
                console.error('음식점 상세 정보를 가져오는데 실패했습니다.', error);
            }
        };

        if (isAuthenticated) {
            fetchRstrDetails();
        } else {
            fetchTokenRstrDetails();
        }

    }, [rstrId]);

    if (!rstrDetails) {
        return <div></div>;
    }

    return (
        <div style={{ padding: '0 3rem' }}>
            <RstrDetailMainContainer>
                <div style={{ margin: '5px 50px', fontSize: '1rem' }}>
                    <span>{rstrDetails.rstr_region}</span>
                    <span> | </span>
                    <span>{rstrDetails.category_name}</span>
                </div>
                <div
                    style={{ display: 'flex' }}>
                    <RstrImgCarusel rstrImg={rstrDetails.rstr_images} />
                    <RstrBriefInfo rstrInfo={rstrDetails} rstrId={rstrId} />
                </div>
            </RstrDetailMainContainer >
            <RstrTabBar rstrInfo={rstrDetails} />
        </div >
    )
}

export default RstrDetailPage;