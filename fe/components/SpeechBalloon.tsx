import React, { useState, useEffect } from 'react';
import { FaPen, FaTrash } from 'react-icons/fa';
import axios from '@/pages/api/axios';
import Image from 'next/image';
import { TimeHandler } from '@/utils/TimeHandler';
import CommentInput from './inputs/CommentInput';

export interface RatingComment {
  ratingId: number;
  ratingCommentId: number;
  userId: number;
  nickname: string;
  userImage: string;
  content: string;
  createdAt: string;
  modifiedAt: string;
}

export interface PairingComment {
  pairingId: number;
  pairingCommentId: number;
  userId: number;
  nickname: string;
  userImage: string;
  content: string;
  createdAt: string;
  modifiedAt: string;
}

type CommentProps = {
  props: RatingComment | PairingComment;
  isMine: boolean;
  deleteFunc: Function;
};

export default function SpeechBalloon({
  props,
  isMine,
  deleteFunc,
}: CommentProps) {
  const [date, setDate] = useState('');
  const [isEditMode, setIsEditMode] = useState(false);
  const [content, setContent] = useState(props.content);

  const editComment = () => {
    setIsEditMode(true);
  };

  const deleteComment = () => {
    if ('ratingCommentId' in props) {
      deleteFunc(props.ratingCommentId);
    }
  };

  useEffect(() => {
    if (props?.modifiedAt !== undefined) {
      let tmpDate = TimeHandler(props?.modifiedAt);
      setDate(tmpDate);
    }
  }, [props?.modifiedAt]);

  return (
    <div className="mx-5 mb-4">
      <div className="w-full h-fit relative ml-4 p-4 rounded-r-lg rounded-b-lg bg-y-cream after:border-t-[30px] after:border-l-[33px] after:border-t-y-cream after:border-l-transparent after:absolute after:top-0 after:-left-8">
        <div className="flex">
          <div className="relative rounded-full w-10 h-10 ml-1">
            <Image
              alt="user profile image"
              src={props.userImage}
              fill
              className="object-cover"
            />
          </div>
          <div className="flex flex-col ml-2">
            <span>{props.nickname}</span>
            <span className="text-xs text-y-gray">{date}</span>
          </div>
        </div>
        {isMine ? (
          <div className="flex-1 flex justify-end items-center text-sm text-y-brown mr-2">
            <button className="flex items-center mr-2" onClick={editComment}>
              <FaPen />
              <span className="text-y-black">수정</span>
            </button>
            <button className="flex items-center" onClick={deleteComment}>
              <FaTrash />
              <span className="text-y-black">삭제</span>
            </button>
          </div>
        ) : null}
        {isEditMode ? (
          <CommentInput
            inputState={content}
            setInputState={setContent}
            postFunc={() => {
              if ('ratingCommentId' in props) {
                axios
                  .patch(`/api/ratings/comments/${props.ratingCommentId}`, {
                    content: content,
                  })
                  .catch((err) => console.log(err));
              }
              setIsEditMode(false);
            }}
          ></CommentInput>
        ) : (
          <div className="m-3 mt-5 text-sm font-light leading-6">{content}</div>
        )}
      </div>
    </div>
  );
}
