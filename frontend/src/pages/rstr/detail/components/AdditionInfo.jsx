import React from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';

const AdditionInfoContainer = styled.div`
    display: flex;
    justify-content: flex-start;
`;

const AdditionInfoContent = styled.div`
    width: 90px;
    height: 90px;
    margin-right: 20px;
    border: 1px solid ${palette.darkblue1};
    border-radius: 50%;
    text-align: center;
    box-shadow: 2px 1px 2px ${palette.gray};
`;

const AdditionInfoLabel = styled.div`
    font-family: 'Gmarket Sans Medium';
    margin: 0 10px;
    font-size: 1.1rem;
`;

const AdditionInfo = ({ rstrInfo }) => {

    const addInfo = {
        "주차가능": rstrInfo.rstr_parking,
        "놀이방 보유": rstrInfo.rstr_play,
        "반려동물 입장가능": rstrInfo.rstr_pet,
        "배달가능": rstrInfo.rstr_delivery,
    }

    return (
        <AdditionInfoContainer style={{ justifyContent: 'flex-start' }}>
            {Object.entries(addInfo).map(([key, value]) => (
                (value) && (
                    <AdditionInfoContent className='centered-flex' key={key}>
                        <AdditionInfoLabel>{key}</AdditionInfoLabel>
                    </AdditionInfoContent>
                )
            ))
            }
        </AdditionInfoContainer >
    )
}

export default AdditionInfo;
