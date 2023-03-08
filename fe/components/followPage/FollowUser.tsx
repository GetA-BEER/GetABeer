import Image from 'next/image';
import SubmitBtn from '../button/SubmitBtn';
import { useEffect, useState } from 'react';
import axios from '@/pages/api/axios';
import CloseBtn from '../button/CloseBtn';
import { useRouter } from 'next/router';
export interface FollowProps {
  userId: number;
  nickname: string;
  imageUrl: string;
  isFollowing: boolean;
}
export default function FollowUser(props: { followprops: FollowProps }) {
  const router = useRouter();
  // const [follow, setFollow] = useState(false);
  // const followClick = () => {
  //   axios
  //     .post(`/api/follows/${props.followprops.userId}`)
  //     .then((res) => {
  //       if (res.data === 'Create Follow') {
  //         setFollow(true);
  //       } else {
  //         setFollow(false);
  //       }
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
  // };
  return (
    <div className=" px-2 m-2 flex justify-between ">
      <div
        className="flex gap-2"
        onClick={() =>
          router.push({
            pathname: `/userpage/${props.followprops.userId}`,
          })
        }
      >
        <Image
          className="h-11 w-11 m-auto mr-1 self-center rounded-full"
          src={props.followprops.imageUrl}
          alt="프로필사진"
          width={40}
          height={40}
        />
        <div className="self-center text-sm">{props.followprops.nickname}</div>
      </div>
      <div className="w-36 self-center">
        {props.followprops.isFollowing === false ? (
          <SubmitBtn onClick={undefined}>팔로우</SubmitBtn>
        ) : (
          <CloseBtn onClick={undefined}>팔로잉</CloseBtn>
        )}
      </div>
    </div>
  );
}
