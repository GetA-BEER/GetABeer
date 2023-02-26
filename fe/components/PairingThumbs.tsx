import { FaThumbsUp, FaRegThumbsUp } from 'react-icons/fa';
import axios from '@/pages/api/axios';
import { useState } from 'react';

// "isUserLikes": true,  "likeCount": 1,
export default function PairingThumbs({
  pairingId,
  isLike,
  setIsLike,
  likeCount,
  setLikeCount,
}: any) {
  const isUserLikeHandler = () => {
    axios
      .post(`/api/pairings/likes?pairingId=${pairingId}`)
      .then((response) => {
        setIsLike(!isLike);
        if (isLike) {
          setLikeCount(likeCount - 1);
        } else {
          setLikeCount(likeCount + 1);
        }
      });
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
