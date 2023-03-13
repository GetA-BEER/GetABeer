export interface RatingCardProps {
  beerId: number;
  ratingId: number;
  korName: string;
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
import { HiTrash, HiOutlineChat } from 'react-icons/hi';
import { FaThumbsUp, FaRegThumbsUp } from 'react-icons/fa';
import { MdModeEdit } from 'react-icons/md';
import { RiAlarmWarningFill } from 'react-icons/ri';
import Tag from '../Tag';
import { TagMatcherToKor } from '@/utils/TagMatcher';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';
import { useRecoilState } from 'recoil';
import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { accessToken, userId } from '@/atoms/login';
import Swal from 'sweetalert2';

export default function RatingCard(props: {
  cardProps: RatingCardProps;
  isMine?: boolean;
  count: number;
}) {
  const router = useRouter();
  const USERID = useRecoilValue(userId);
  const [isLogin, setIsLogin] = useState(false);
  const [TOKEN] = useRecoilState(accessToken);
  const config = {
    headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
    withCredentials: true,
  };

  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);
  const [isLike, setIsLike] = useState<boolean>(props.cardProps.isUserLikes);
  const [likeCount, setLikeCount] = useState<number>(props.cardProps.likeCount);
  const editRating = () => {
    router.replace(`/editrating/${props.cardProps.ratingId}`);
  };

  const deleteRating = () => {
    axios.delete(`/api/ratings/${props.cardProps.ratingId}`, config);
    router.back();
  };

  const isUserLikeHandler = () => {
    axios
      .post(
        `/api/ratings/likes?ratingId=${props.cardProps.ratingId}`,
        {},
        config
      )
      .then((res) => {
        setIsLike(!isLike);
        if (isLike) {
          setLikeCount(likeCount - 1);
        } else {
          setLikeCount(likeCount + 1);
        }
      });
  };
  const userCheck = () => {
    if (USERID !== props.cardProps.userId) {
      router.push(`/userpage/${props.cardProps.userId}`);
    } else {
      router.push(`/mypage`);
    }
  };
  return (
    <div>
      <div className="flex justify-between">
        <div className="flex">
          <div
            className="relative rounded-full w-7 h-7 ml-1"
            onClick={userCheck}
          >
            <Image
              alt="user profile image"
              src={props.cardProps.userImage}
              fill
              sizes="100vw"
              className="object-cover rounded-full"
            />
          </div>
          <div className="flex flex-col ml-2 text-xs" onClick={userCheck}>
            <span>{props.cardProps.nickname}</span>
            <span className="text-[5px] text-y-gray">
              {ToDateString(props.cardProps.modifiedAt)}
            </span>
          </div>
          <span className="text-sm m-1">⭐️ {props.cardProps.star}</span>
        </div>
        <div>
          {props.isMine ? (
            <div className="flex-1 flex items-center text-sm text-y-brown mr-2">
              <button className="flex items-center mr-2" onClick={editRating}>
                <MdModeEdit />
                <span className="text-y-black text-xs">수정</span>
              </button>
              <button
                className="flex items-center"
                onClick={() => {
                  Swal.fire({
                    title: '게시글을 삭제하시겠습니까?',
                    text: '삭제하시면 다시 복구시킬 수 없습니다.',
                    showCancelButton: true,
                    confirmButtonColor: '#f1b31c',
                    cancelButtonColor: '#A7A7A7',
                    confirmButtonText: '삭제',
                    cancelButtonText: '취소',
                  }).then((result) => {
                    if (result.isConfirmed) {
                      deleteRating();
                    }
                  });
                }}
              >
                <HiTrash />
                <span className="text-y-black text-xs">삭제</span>
              </button>
            </div>
          ) : router.pathname === '/rating/[ratingid]' ? (
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
          ) : null}
        </div>
      </div>
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
          <span className="text-[8px] ml-0.5 mr-1 mt-0.5">{props.count}</span>
        </div>
        <button
          className="flex justify-center items-center"
          onClick={
            isLogin
              ? isUserLikeHandler
              : () => {
                  Swal.fire({
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
                }
          }
        >
          {isLike ? (
            <FaThumbsUp className="w-3 h-3" />
          ) : (
            <FaRegThumbsUp className="w-3 h-3" />
          )}
          <span className="text-[8px] ml-0.5 mr-1 mt-0.5">{likeCount}</span>
        </button>
      </div>
    </div>
  );
}
