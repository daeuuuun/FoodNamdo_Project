import React, { useEffect, useState } from 'react';
import "./MyFavorite.css";
import RstrCard from './../rstr/list/components/RstrCard';
import { authBackInstance } from '../../utils/axiosInstance';

const MyFavorite = () => {

	const [rstrList, setRstrList] = useState([]);
	const [totalFavorite, setTotalFavorite] = useState(0);

	const getFavoriteRstr = async () => {
		const url = `/mypage/getFavoriteRstr`;

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
				{rstrList.length > 0 &&
					rstrList.map((rstrInfo, index) => (
						<RstrCard key={index} rstrInfo={rstrInfo} mode={'myFavorite'} />
					))
				}
			</div>
		</div>
	)
}

export default MyFavorite;