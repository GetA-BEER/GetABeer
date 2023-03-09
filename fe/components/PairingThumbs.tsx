import { FaThumbsUp, FaRegThumbsUp } from 'react-icons/fa';
import axios from '@/pages/api/axios';
import { useRouter } from 'next/router';
import Swal from 'sweetalert2';
import { useRecoilState } from 'recoil';
import { accessToken } from '@/atoms/login';

// "isUserLikes": true,  "likeCount": 1,
export default function PairingThumbs({
  isLogin,
  pairingId,
  isLike,
  setIsLike,
  likeCount,
  setLikeCount,
}: any) {
  const [TOKEN] = useRecoilState<string>(accessToken);

  const router = useRouter();
  const goToLogin = () => {
    Swal.fire({
      text: '로그인이 필요한 서비스 입니다.',
      showCancelButton: true,
      confirmButtonColor: '#f1b31c',
      cancelButtonColor: '#A7A7A7',
      confirmButtonText: '로그인',
      cancelButtonText: '취소',
    }).then((result) => {
      if (result.isConfirmed) {
        router.push({
          pathname: '/login',
        });
      }
    });
  };
  const isUserLikeHandler = () => {
    if (isLogin) {
      axios
        .post(`/api/pairings/likes?pairingId=${pairingId}`)
        .then((response) => {
          setIsLike(!isLike);
          if (isLike) {
            setLikeCount(likeCount - 1);
          } else {
            setLikeCount(likeCount + 1);
          }
        })
        .catch((error) => console.log(error));
    } else {
      goToLogin();
    }
  };

  return (
    <button
      className="flex justify-center items-center text-[8px] ml-1"
      onClick={isUserLikeHandler}
    >
      <span>
        {isLike ? (
          <FaThumbsUp className="w-3 h-3 mb-0.5" />
        ) : (
          <FaRegThumbsUp className="w-3 h-3 mb-0.5" />
        )}
      </span>
      <span className="mr-0.5">{likeCount}</span>
    </button>
  );
}
