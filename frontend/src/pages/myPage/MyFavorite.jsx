import React, { useEffect, useState } from 'react';
import "./MyFavorite.css";
import RstrCard from './../rstr/list/components/RstrCard';
import { BACKEND_SERVER_URL } from '../../config/Config';
import { authBackInstance } from '../../utils/axiosInstance';

const MyFavorite = () => {

	const [rstrList, setRstrList] = useState([]);
	const [totalFavorite, setTotalFavorite] = useState(0);

	const getFavoriteRstr = async () => {
		const url = `${BACKEND_SERVER_URL}/mypage/getFavoriteRstr`;

		const token = localStorage.getItem('accessToken');

		try {
			const response = await authBackInstance.get(url);
			setRstrList(response.data.rstr);
			setTotalFavorite(response.data.total_favorite);
		} catch (error) {
			console.log(error);
		}
	}

	useEffect(() => {
		getFavoriteRstr();
	}, [])

	return (
		<div className='my-favorite-container'>
			<div className='my-favorite-count'>총 {totalFavorite} 개</div>
			<div className='my-favorite-list'>
				{rstrList.length > 0 ? (
					rstrList.map((rstrInfo, index) => (
						<RstrCard key={index} rstrInfo={rstrInfo} mode={'myFavorite'} />
					))
				) : (
					<div className='my-favorite-list-empty'>
						즐겨찾는 음식점이 없습니다.
					</div>
				)}
			</div>
		</div>
	)
}

export default MyFavorite;