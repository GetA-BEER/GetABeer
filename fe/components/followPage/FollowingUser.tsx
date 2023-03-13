import Image from 'next/image';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { accessToken, userId } from '@/atoms/login';
import { useRecoilValue } from 'recoil';
import FollowBtn from './FollowBtn';

export interface FollowingProps {
  userId: number;
  nickname: string;
  imageUrl: string;
  isFollowing: boolean;
}
export default function FollowingUser(props: {
  followingProps: FollowingProps;
}) {
  const [TOKEN] = useRecoilValue(accessToken);
  const router = useRouter();
  const [isFollow, setIsFollow] = useState<boolean>(
    props.followingProps.isFollowing
  );
  const [isLogin, setIsLogin] = useState(false);
  const USERID = useRecoilValue(userId);
  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  const userCheck = () => {
    if (USERID !== props.followingProps.userId) {
      router.push(`/userpage/${props.followingProps.userId}`);
    } else {
      router.push(`/mypage`);
    }
  };
  return (
    <div className=" px-2 m-2 flex justify-between ">
      <div className="flex gap-2" onClick={userCheck}>
        <Image
          className="h-11 w-11 m-auto mr-1 self-center rounded-full"
          src={props.followingProps.imageUrl}
          alt="프로필사진"
          width={40}
          height={40}
        />
        <div className="self-center text-sm">
          {props.followingProps.nickname}
        </div>
      </div>
      <FollowBtn
        id={props.followingProps.userId}
        isFollow={isFollow}
        setIsFollow={setIsFollow}
      />
    </div>
  );
}
