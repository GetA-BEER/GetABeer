import Image from 'next/image';
import SubmitBtn from '../button/SubmitBtn';
import { useEffect, useState } from 'react';
import axios from '@/pages/api/axios';
import CloseBtn from '../button/CloseBtn';
import { useRouter } from 'next/router';
import { accessToken, userId } from '@/atoms/login';
import { useRecoilValue } from 'recoil';
import Swal from 'sweetalert2';
export interface FollowProps {
  userId: number;
  nickname: string;
  imageUrl: string;
  isFollowing: boolean;
}
export default function FollowUser(props: { followprops: FollowProps }) {
  const [TOKEN] = useRecoilValue(accessToken);
  const router = useRouter();
  const [follow, setFollow] = useState<boolean>(props.followprops.isFollowing);
  const [isLogin, setIsLogin] = useState(false);
  const USERID = useRecoilValue(userId);
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
  const followClick = () => {
    axios
      .post(`/api/follows/${props.followprops.userId}`)
      .then((res) => {
        if (res.data === 'Create Follow') {
          setFollow(true);
        } else {
          setFollow(false);
        }
      })
      .catch((err) => {
        console.log(err);
        goToLogin();
      });
  };
  const userCheck = () => {
    if (USERID !== props.followprops.userId) {
      router.push(`/userpage/${props.followprops.userId}`);
    } else {
      router.push(`/mypage`);
    }
  };
  return (
    <div className=" px-2 m-2 flex justify-between ">
      <div className="flex gap-2" onClick={userCheck}>
        <Image
          className="h-11 w-11 m-auto mr-1 self-center rounded-full"
          src={props.followprops.imageUrl}
          alt="프로필사진"
          width={40}
          height={40}
        />
        <div className="self-center text-sm">{props.followprops.nickname}</div>
      </div>
      <div className="w-32 self-center">
        {USERID !== props.followprops.userId ? (
          <div>
            {follow === false ? (
              <SubmitBtn onClick={followClick}>팔로우</SubmitBtn>
            ) : (
              <CloseBtn onClick={followClick}>팔로잉</CloseBtn>
            )}
          </div>
        ) : (
          <div className="my-2 p-3 text-center text-sm">🍋 Me 🍋</div>
        )}
      </div>
    </div>
  );
}
