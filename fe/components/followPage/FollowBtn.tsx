import SubmitBtn from '../button/SubmitBtn';
import { useEffect, useState } from 'react';
import axios from '@/pages/api/axios';
import CloseBtn from '../button/CloseBtn';
import { useRouter } from 'next/router';
import { accessToken, userId } from '@/atoms/login';
import { useRecoilValue, useRecoilState } from 'recoil';
import Swal from 'sweetalert2';

export interface BtnProps {
  id: number;
  isFollow: boolean;
  setIsFollow: React.Dispatch<React.SetStateAction<boolean>>;
}
export default function FollowBtn({ id, isFollow, setIsFollow }: BtnProps) {
  const [TOKEN] = useRecoilState(accessToken);
  const router = useRouter();
  const [isLogin, setIsLogin] = useState(false);
  const USERID = useRecoilValue(userId);
  const userid = id;
  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  const goToLogin = () => {
    Swal.fire({
      title: 'Get A Beer',
      text: '로그인이 필요한 서비스 입니다.',
      confirmButtonColor: '#F1B31C',
      confirmButtonText: '확인',
    }).then(() => {
      router.push({
        pathname: '/login',
      });
    });
  };
  const followClick = () => {
    const config = {
      headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
      withCredentials: true,
    };
    axios
      .post(`/api/follows/${id}`, {}, config)
      .then((res) => {
        setIsFollow(!isFollow);
      })
      .catch((err) => {
        console.log(err);
        goToLogin();
      });
  };
  return (
    <div className="w-32 self-center" onClick={followClick}>
      {USERID !== userid ? (
        <div>
          {!isFollow ? (
            <SubmitBtn onClick={undefined}>팔로우</SubmitBtn>
          ) : (
            <CloseBtn onClick={undefined}>팔로잉</CloseBtn>
          )}
        </div>
      ) : (
        <div className="my-2 p-3 text-center text-sm">🍋 Me 🍋</div>
      )}
    </div>
  );
}
