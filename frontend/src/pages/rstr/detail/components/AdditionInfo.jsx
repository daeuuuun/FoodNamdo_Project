import React from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import PetsIcon from '@mui/icons-material/Pets';
import LocalParkingIcon from '@mui/icons-material/LocalParking';
import DeliveryDiningIcon from '@mui/icons-material/DeliveryDining';

const AdditionInfoContainer = styled.div`
    display: flex;
`;

const AdditionInfoContent = styled.div`
    width: 5.2rem;
    height: 5.2rem;
    margin-right: 20px;
    border: 1px solid ${palette.darkblue1};
    border-radius: 50%;
    text-align: center;
     box-shadow: 2px 1px 2px ${palette.gray};
`;

const AdditionInfoIcon = styled.div`
    width: 2.5rem;
    height: 2.5rem;
    border: 1px solid black;
`;

const AdditionInfoLabel = styled.div`
    font-family: 'Gmarket Sans Medium';
    margin: 0 0.6rem 0 0.6rem;
    font-size: 0.8rem;
`;

const addInfo = {
    "주차가능": true,
    "놀이방보유": true,
    "반려동물 입장가능": true,
    "배달서비스": true,
}

const iconMap = {
    "주차가능": LocalParkingIcon,
    "놀이방보유": PetsIcon,
    "반려동물 입장가능": PetsIcon,
    "배달서비스": DeliveryDiningIcon,
}

const AdditionInfo = () => {
    return (
        <AdditionInfoContainer>
            {Object.entries(addInfo).map(([key, value]) => (
                value && (
                    <AdditionInfoContent className='centered-flex' key={key}>
                        <AdditionInfoLabel>{key}</AdditionInfoLabel>
                    </AdditionInfoContent>
                )
            ))}
        </AdditionInfoContainer>
    )
}

export default AdditionInfo;
