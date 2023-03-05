import { FaRegCommentDots } from 'react-icons/fa';
import { FaThumbsUp, FaRegThumbsUp } from 'react-icons/fa';
import Image from 'next/image';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import SmallTag from '@/components/smallCards/SmallTag';
import { TimeHandler } from '@/utils/TimeHandler';
import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import axios from '@/pages/api/axios';
import { useRouter } from 'next/router';
import { accessToken } from '@/atoms/login';
import Swal from 'sweetalert2';

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

export default function SmallRatingCard({ ratingProps }: any) {
  const [RatingList, setRatingPropsList] = useState<any>();
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [starScore, setStarScore] = useState<number>(ratingProps?.star);
  const [collisions, setCollisions] = useState<boolean>(false);
  const [randomNum, setRandomNum] = useState(0);
  const [date, setDate] = useState<string>('');
  const initialDate = ratingProps?.createdAt;
  const [isLike, setIsLike] = useState<boolean>(ratingProps.isUserLikes);
  const [likeCount, setLikeCount] = useState<number>(ratingProps.likeCount);
  const router = useRouter();
  const TOKEN = useRecoilValue(accessToken);
  const [isLogin, setIsLogin] = useState<boolean>(false);
  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  const goToLogin = () => {
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
  };

  // ratingPropsList 초기화
  useEffect(() => {
    if (ratingProps !== undefined) {
      setRatingPropsList(ratingProps);
      setLikeCount(ratingProps.likeCount);
      setIsLike(ratingProps.isUserLikes);
    }
  }, [ratingProps]);

  // 글 작성 없는 경우
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
    if (starScore) {
      const starNum = Number(starScore?.toFixed(2));
      setStarScore(starNum);
    } else {
      setStarScore(0);
    }
  }, [starScore]);

  // 날짜 초기화
  useEffect(() => {
    if (initialDate !== undefined) {
      let tmpDate = TimeHandler(initialDate);
      setDate(tmpDate);
    }
  }, [initialDate]);

  // 더보기 설정
  useEffect(() => {
    if (RatingList !== undefined) {
      let parent = document.getElementById('overParents');
      let tagChild = document.getElementById(`overTags${RatingList?.ratingId}`);
      let deschild = document.getElementById(
        `overDescribe${RatingList?.ratingId}`
      );
      if (parent !== null && deschild !== null && tagChild !== null) {
        let parentHeight = parent.offsetHeight;
        let tagChildHeight = tagChild.clientHeight;
        let deschildHeight = deschild.clientHeight;
        if (parentHeight <= tagChildHeight + deschildHeight)
          setCollisions(true);
      }
    }
  }, [collisions, RatingList]);

  const isUserLikeHandler = () => {
    if (isLogin) {
      axios
        .post(`/api/ratings/likes?ratingId=${ratingProps.ratingId}`)
        .then((res) => {
          setIsLike(!isLike);
          if (isLike) {
            setLikeCount(likeCount - 1);
          } else {
            setLikeCount(likeCount + 1);
          }
        });
    } else {
      goToLogin();
    }
  };

  return (
    <>
      <div className="w-full rounded-lg ml-2 mb-2 bg-white text-y-black drop-shadow-lg text-[8px] border">
        {/* 별점,닉네임 */}

        <div className="flex justify-between py-1 px-2">
          <span className="flex justify-center items-center">
            <Image
              src="/images/star.png"
              alt="star"
              width={20}
              height={20}
              className="mr-1 mb-[3px] text-y-gold drop-shadow-md  select-none"
              priority
            />
            {starScore}
          </span>
          <span className="flex justify-end items-center w-2/5 text-[8px]">
            <span className="w-[70%] text-end truncate pr-[2px]">
              {RatingList?.nickname}
            </span>
            {RatingList?.userImage === null ? (
              <></>
            ) : (
              <Image
                src={RatingList?.userImage}
                alt="userImg"
                width={100}
                height={100}
                className="w-4 h-4"
                priority
              />
            )}
          </span>
        </div>
        {/* 태그, 설명 */}
        <Link href={`/rating/${RatingList?.ratingId}`}>
          <div
            className={`py-2 px-1 h-28 w-full border-y-2 relative ${
              collisions ? 'overflow-hidden' : ''
            }`}
            id="overParents"
          >
            <div id={`overTags${RatingList?.ratingId}`}>
              <SmallTag tags={RatingList?.ratingTag} />
            </div>
            {RatingList?.content === undefined || RatingList?.content === '' ? (
              <div className="text-y-gray ">
                {noReviewState[randomNum]?.contents}
              </div>
            ) : collisions ? (
              <>
                <div
                  className="text-[8] leading-5 h-fit relative"
                  id={`overDescribe${RatingList?.ratingId}`}
                >
                  {RatingList?.content}
                </div>
                <div className="absolute bottom-0.5  right-1 px-1 bg-white">
                  ...<span className="text-y-gold">더보기</span>
                </div>
              </>
            ) : (
              <div
                className="text-xs leading-5 h-fit"
                id={`overDescribe${RatingList?.ratingId}`}
              >
                {RatingList?.content}
              </div>
            )}
          </div>
        </Link>

        {/* 날짜,코멘트수,엄지수 */}
        <div className="p-2 flex justify-between items-center">
          <div className="text-y-gray">{date}</div>
          <div className="flex">
            <span className="flex justify-center">
              <FaRegCommentDots className="mr-[2px] mt-[1px] w-3 h-3" />
              {RatingList?.commentCount}
            </span>
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
          </div>
        </div>
      </div>
    </>
  );
}
