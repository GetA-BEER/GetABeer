export interface RatingCardProps {
  beerId: number;
  commentCount: number;
  content: string;
  createdAt: string;
  likeCount: number;
  modifiedAt: string;
  nickname: string;
  ratingId: number;
  ratingTag: [string, string, string, string];
  star: number;
  userId: number;
  //여기 유저 이미지 url도 들어와야함!
}
import { ToDateString } from '@/utils/ToDateString';
import { BiUser } from 'react-icons/bi';
import { HiOutlineChat } from 'react-icons/hi';
import { FiThumbsUp } from 'react-icons/fi';
import { FaPen, FaTrash } from 'react-icons/fa';
import Tag from '../Tag';
import { TagMatcherToKor } from '@/utils/TagMatcher';
import { useRouter } from 'next/router';
import axios from 'axios';

export default function RatingCard(props: {
  cardProps: RatingCardProps;
  isMine: boolean;
}) {
  const router = useRouter();
  const editRating = () => {
    router.push(`/editrating/${props.cardProps.ratingId}`);
  };

  const deleteRating = () => {
    axios.delete(`/api/ratings/${props.cardProps.ratingId}`);
    router.back();
  };

  return (
    <div>
      <div className="flex">
        <BiUser className=" bg-y-brown text-white rounded-full w-10 h-10 ml-1" />
        <div className="flex flex-col ml-2">
          <span>{props.cardProps.nickname}</span>
          <span className="text-xs text-y-gray">
            {ToDateString(props.cardProps.modifiedAt)}
          </span>
        </div>
        <span className="text-sm m-1">⭐️ {props.cardProps.star}</span>
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
      </div>
      <div className="m-2">
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
          <span className="text-sm ml-0.5 mr-2 mt-0.5">
            {props.cardProps.commentCount}
          </span>
        </div>
        <div className="flex justify-center items-center">
          <FiThumbsUp />
          <span className="text-sm ml-0.5 mr-1 mt-0.5">
            {props.cardProps.likeCount}
          </span>
        </div>
      </div>
    </div>
  );
}
