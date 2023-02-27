import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import axios from '@/pages/api/axios';

interface WishHeartProps {
  beerId: number;
  isWish: boolean;
  setIsWish: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function WishHeart({
  beerId,
  isWish,
  setIsWish,
}: WishHeartProps) {
  const handleWish = () => {
    axios
      .patch(`/api/beers/${beerId}/wish`)
      .then((res) => {
        console.log(res.data);
        setIsWish(!isWish);
      })
      .catch((error) => console.log(error));
  };
  return (
    <button className="text-3xl mb-1" onClick={handleWish}>
      {isWish ? (
        <AiFillHeart className="text-y-brown" />
      ) : (
        <AiOutlineHeart className="text-y-gray" />
      )}
    </button>
  );
}
