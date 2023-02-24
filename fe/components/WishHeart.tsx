import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import axios from '@/pages/api/axios';
export default function WishHeart({ beerId, isWish, setWish }: any) {
  const handleWish = () => {
    axios
      .patch(`/api/beers/${beerId}/wish`)
      .then((response) => {
        // console.log(response.data);
        setWish(!isWish);
      })
      .catch((error) => console.log(error));
  };
  return (
    <div onClick={handleWish}>
      {isWish ? <AiOutlineHeart /> : <AiFillHeart />}
    </div>
  );
}
