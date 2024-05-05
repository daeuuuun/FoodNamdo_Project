import React, { useEffect, useState } from 'react';

const RstrMap = ({ rstrInfo }) => {
    const [isKakaoMapLoaded, setKakaoMapLoaded] = useState(false);

    useEffect(() => {
        // 카카오 맵 스크립트 로드 상태를 확인합니다.
        const kakaoScript = document.createElement('script');
        kakaoScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=ac53ae1240c1d71e1585cd788084a7ca&autoload=false`;
        document.head.appendChild(kakaoScript);

        kakaoScript.onload = () => {
            window.kakao.maps.load(() => {
                setKakaoMapLoaded(true);
            });
        };
        console.log(rstrInfo);
    }, []);

    useEffect(() => {
        if (isKakaoMapLoaded && rstrInfo) {
            // Kakao 지도 API를 이용하여 지도를 생성합니다.
            const container = document.getElementById('map'); // 지도를 표시할 div
            const options = {
                center: new window.kakao.maps.LatLng(rstrInfo.rstr_la, rstrInfo.rstr_lo), // 지도의 중심 좌표
                level: 3, // 지도의 확대 레벨
            };

            const map = new window.kakao.maps.Map(container, options); // 지도 생성 및 객체 리턴

            // 마커가 표시될 위치를 지정합니다 
            const markerPosition = new window.kakao.maps.LatLng(rstrInfo.rstr_la, rstrInfo.rstr_lo); // 수정된 부분

            // 마커를 생성합니다
            const marker = new window.kakao.maps.Marker({
                position: markerPosition,
            });

            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(map);
        }
    }, [isKakaoMapLoaded, rstrInfo]); // isKakaoMapLoaded 상태가 변경될 때만 실행

    return <div id="map" style={{ marginTop: '5px', width: '600px', height: '20rem' }}></div>;
};

export default RstrMap;
