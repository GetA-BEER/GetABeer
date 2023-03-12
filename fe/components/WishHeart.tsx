import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';
import Swal from 'sweetalert2';
import { useRecoilState } from 'recoil';
import { accessToken } from '@/atoms/login';

interface WishHeartProps {
  beerId: number;
  isWish: boolean;
  setIsWish: React.Dispatch<React.SetStateAction<boolean>>;
  isLogin: boolean;
}

export default function WishHeart({
  beerId,
  isWish,
  setIsWish,
  isLogin,
}: WishHeartProps) {
  const router = useRouter();
  const [TOKEN] = useRecoilState(accessToken);
  const config = {
    headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
    withCredentials: true,
  };
  const handleWish = () => {
    if (isLogin) {
      axios
        .patch(`/api/beers/${beerId}/wish`, {}, config)
        .then((res) => {
          setIsWish(!isWish);
        })
        .catch((error) => console.log(error));
    } else {
      Swal.fire({
        text: '로그인이 필요한 서비스 입니다',
      }).then(() => {
        router.push({
          pathname: '/login',
        });
      });
    }
  };
  return (
    <button className="text-3xl mb-1 px-1" onClick={handleWish}>
      {isWish ? (
        <AiFillHeart className="text-y-brown" />
      ) : (
        <AiOutlineHeart className="text-y-gray" />
      )}
    </button>
  );
}
