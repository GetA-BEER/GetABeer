export interface RatingCardProps {
  beerId: number;
  ratingId: number;
  userId: number;
  nickname: string;
  userImage: string;
  star: number;
  ratingTag: [string, string, string, string];
  content: string;
  likeCount: number;
  commentCount: number;
  createdAt: string;
  modifiedAt: string;
  isUserLikes: boolean;
}
import { ToDateString } from '@/utils/ToDateString';
import Image from 'next/image';
import { HiOutlineChat } from 'react-icons/hi';
import { FaThumbsUp, FaRegThumbsUp } from 'react-icons/fa';
import { FaPen, FaTrash } from 'react-icons/fa';
import Tag from '../Tag';
import { TagMatcherToKor } from '@/utils/TagMatcher';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';
import { useState } from 'react';

export default function RatingCard(props: {
  cardProps: RatingCardProps;
  isMine: boolean;
  count: number;
}) {
  const router = useRouter();
  const [isLike, setIsLike] = useState<boolean>(props.cardProps.isUserLikes);
  const [likeCount, setLikeCount] = useState<number>(props.cardProps.likeCount);
  const editRating = () => {
    router.replace(`/editrating/${props.cardProps.ratingId}`);
  };

  const deleteRating = () => {
    axios.delete(`/api/ratings/${props.cardProps.ratingId}`);
    router.back();
  };

  const isUserLikeHandler = () => {
    axios
      .post(`/api/ratings/likes?ratingId=${props.cardProps.ratingId}`)
      .then((res) => {
        setIsLike(!isLike);
        if (isLike) {
          setLikeCount(likeCount - 1);
        } else {
          setLikeCount(likeCount + 1);
        }
      });
  };

  return (
    <div>
      <div className="flex">
        <div className="relative rounded-full w-10 h-10 ml-1">
          <Image
            alt="user profile image"
            src={props.cardProps.userImage}
            fill
            className="object-cover"
          />
        </div>
        <div className="flex flex-col ml-2">
          <span>{props.cardProps.nickname}</span>
          <span className="text-xs text-y-gray">
            {ToDateString(props.cardProps.modifiedAt)}
          </span>
        </div>
        <span className="text-sm m-1">⭐️ {props.cardProps.star}</span>
      </div>
      {props.isMine ? (
        <div className="flex-1 flex justify-end items-center text-sm text-y-brown mr-2">
          <button className="flex items-center mr-2" onClick={editRating}>
            <FaPen />
            <span className="text-y-black">수정</span>
          </button>
          <button className="flex items-center" onClick={deleteRating}>
            <FaTrash />
            <span className="text-y-black">삭제</span>
          </button>
        </div>
      ) : null}
      <div className="m-2 flex flex-wrap">
        {props.cardProps.ratingTag.map((el, idx) => {
          return <Tag key={idx}>{TagMatcherToKor(el)}</Tag>;
        })}
      </div>
      <div className="m-2">
        <p>{props.cardProps.content}</p>
      </div>
      <div className="flex justify-end mr-1">
        <div className="flex justify-center items-center">
          <HiOutlineChat />
          <span className="text-sm ml-0.5 mr-2 mt-0.5">{props.count}</span>
        </div>
        <button
          className="flex justify-center items-center"
          onClick={isUserLikeHandler}
        >
          {isLike ? <FaThumbsUp /> : <FaRegThumbsUp />}
          <span className="text-sm ml-0.5 mr-1 mt-0.5">{likeCount}</span>
        </button>
      </div>
    </div>
  );
}
