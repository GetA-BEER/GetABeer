import React, { useState, useEffect } from 'react';
import { HiTrash } from 'react-icons/hi';
import { MdModeEdit } from 'react-icons/md';
import { RiAlarmWarningFill } from 'react-icons/ri';
import axios from '@/pages/api/axios';
import { TimeHandler } from '@/utils/TimeHandler';
import CommentInput from './inputs/CommentInput';
import Swal from 'sweetalert2';
import ProfileCard from './pairing/ProfileCard';

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

  const patchComment = () => {
    if ('ratingCommentId' in props) {
      axios
        .patch(`/api/ratings/comments/${props.ratingCommentId}`, {
          content: content,
        })
        .catch((err) => console.log(err));
    }
    if ('pairingCommentId' in props) {
      axios
        .patch(`/api/pairings/comments/${props.pairingCommentId}`, {
          content: content,
        })
        .catch((err) => console.log(err));
    }
    setIsEditMode(false);
  };

  const deleteComment = () => {
    if ('ratingCommentId' in props) {
      deleteFunc(props.ratingCommentId);
    }
    if ('pairingCommentId' in props) {
      deleteFunc(props.pairingCommentId);
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
      <div className="w-full h-fit relative ml-4 p-1 rounded-r-xl rounded-b-xl bg-y-cream after:border-t-[17px] after:border-l-[20px] after:border-t-y-cream after:border-l-transparent after:absolute after:top-0 after:-left-5">
        <div className="flex items-center">
          <ProfileCard
            nickname={props?.nickname}
            date={date}
            userImage={props?.userImage}
            userId={props?.userId}
          />
          {isMine ? (
            <div className="flex-1 flex justify-end items-center  text-y-brown mr-3 text-xs">
              <button className="flex items-center mr-1" onClick={editComment}>
                <MdModeEdit className="mb-[1px]" />
                <span className="text-y-black ml-[1px]">수정</span>
              </button>
              <button
                className="flex items-center"
                onClick={() => {
                  Swal.fire({
                    text: '댓글을 삭제하시겠습니까?',
                    // text: '삭제하시면 다시 복구시킬 수 없습니다.',
                    showCancelButton: true,
                    confirmButtonColor: '#f1b31c',
                    cancelButtonColor: '#A7A7A7',
                    confirmButtonText: '삭제',
                    cancelButtonText: '취소',
                  }).then((result) => {
                    if (result.isConfirmed) {
                      deleteComment();
                    }
                  });
                }}
              >
                <HiTrash className="text-y-brown inline mb-[1px]" />
                <span className="text-y-black">삭제</span>
              </button>
            </div>
          ) : (
            <div className="flex-1 flex justify-end items-center  text-y-brown mr-3 text-xs">
              <button
                className="flex items-center mr-1"
                onClick={() => {
                  console.log('신고하기');
                }}
              >
                <RiAlarmWarningFill className="mb-[1px]" />
                <span className="text-y-black ml-[1px]">신고하기</span>
              </button>
            </div>
          )}
        </div>
        {isEditMode ? (
          <CommentInput
            inputState={content}
            setInputState={setContent}
            postFunc={patchComment}
          ></CommentInput>
        ) : (
          <div className="my-3 mx-5 mt-1 text-xs sm:text-sm font-light leading-6">
            {content}
          </div>
        )}
      </div>
    </div>
  );
}
