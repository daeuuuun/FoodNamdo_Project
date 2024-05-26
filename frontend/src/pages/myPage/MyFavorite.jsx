import React, { useEffect, useState } from 'react';
import "./MyFavorite.css";
import RstrCard from './../rstr/list/components/RstrCard';
import { BACKEND_SERVER_URL } from '../../config/Config';
import axios from 'axios';
const MyFavorite = () => {

	const [rstr, setRstr] = useState({});

	const getFavoriteRstr = async () => {
		const url = `${BACKEND_SERVER_URL}/mypage/getFavoriteRstr?user_id=${1}&page=${1}`;
		try {
			const response = await axios.get(url);
			setRstr(response.data);

		} catch (error) {
			console.log(error);
		}
	}

	useEffect(() => {
		getFavoriteRstr();
	}, [])

	return (
		<div className='my-favorite-container'>
			{/* {rstr && rstr.rstr.map((rstrInfo) => (
				<RstrCard rstrInfo={rstrInfo} />
			))} */}
			<div>{rstr.total_favorite}</div>
		</div>
	)
}

export default MyFavorite;