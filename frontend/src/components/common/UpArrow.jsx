import styled from "styled-components";
import palette from '../../styles/palette';
import ArrowCircleUpIcon from '@mui/icons-material/ArrowCircleUp';

const UpArrowContainer = styled.div`
    display: flex;
    justify-content: flex-end;
    position: fixed;
    // right: 20px; 
    bottom: 20px;
    cursor: pointer;
`;

const StyledIcon = styled(ArrowCircleUpIcon)`
    color: ${palette.darkblue1};
    margin-bottom: -6px;
    font-size: 3.5rem;
`;

const UpArrow = () => {
    const MoveToTop = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }
    return (
        <UpArrowContainer>
            <StyledIcon onClick={MoveToTop} style={{ fontSize: '3.5rem' }} />
        </UpArrowContainer>
    )
}

export default UpArrow;
