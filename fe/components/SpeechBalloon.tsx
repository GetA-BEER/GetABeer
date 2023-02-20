import React, { useState, useEffect } from 'react';
import { BiUser } from 'react-icons/bi';
import { FaPen, FaTrash } from 'react-icons/fa';
import { useRouter } from 'next/router';
// import axios from '@/pages/api/axios';
import { TimeHandler } from '@/utils/TimeHandler';

type CommentProps = {
  props: {
    ratingId: number;
    ratingCommentId: number;
    userId: number;
    nickname: string;
    content: string;
    createdAt: string;
    modifiedAt: string;
    //유저 프로필이미지
  };
  isMine: boolean;
};

export default function SpeechBalloon({ props, isMine }: CommentProps) {
  const router = useRouter();
  const editRatingComment = () => {
    // router.replace(`/editrating/${props.cardProps.ratingId}`);
  };

  const deleteRatingComment = () => {
    // axios.delete(`/ratings/${props.cardProps.ratingId}`);
    // router.back();
  };

  const [date, setDate] = useState<any>('');

  useEffect(() => {
    if (props?.modifiedAt !== undefined) {
      let tmpDate = TimeHandler(props?.modifiedAt);
      setDate(tmpDate);
    }
  }, [props?.modifiedAt]);
  console.log('DateDateDate', typeof date);

  return (
    <div className="mx-5 mb-4">
      <div className="w-full h-fit relative ml-4 p-4 rounded-r-lg rounded-b-lg bg-y-cream after:border-t-[30px] after:border-l-[33px] after:border-t-y-cream after:border-l-transparent after:absolute after:top-0 after:-left-8">
        <div className="flex">
          <BiUser className=" bg-y-brown text-white rounded-full w-10 h-10 ml-1" />
          <div className="flex flex-col ml-2">
            <span>{props.nickname}</span>
            <span className="text-xs text-y-gray">{date}</span>
          </div>
        </div>
        {isMine ? (
          <div className="flex-1 flex justify-end items-center text-sm text-y-brown mr-2">
            <button
              className="flex items-center mr-2"
              onClick={editRatingComment}
            >
              <FaPen />
              <span className="text-y-black">수정</span>
            </button>
            <button className="flex items-center" onClick={deleteRatingComment}>
              <FaTrash />
              <span className="text-y-black">삭제</span>
            </button>
          </div>
        ) : null}
        <div className="m-3 mt-5 text-sm font-light leading-6">
          {props.content}
        </div>
      </div>
    </div>
  );
}
