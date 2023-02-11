import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { SmallCardInfo } from './SmallCardController';
import Image from 'next/image';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { noReview } from '@/atoms/noReview';
import { NoReviewTypes } from '@/atoms/noReview';
import SmallTag from '@/components/smallCards/SmallTag';

export default function SmallCard(props: { cardProps: SmallCardInfo }) {
  const MAX_LEN = 42;
  const [starScore, setStarScore] = useState<number | undefined>(
    props?.cardProps?.star
  );
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
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

  return (
    <div className="w-full rounded-lg bg-white text-y-black drop-shadow-lg text-xs border">
      {/* 별점,닉네임 */}
      <div className="flex justify-between py-1 px-2">
        <span className="flex justify-center items-center">
          <Image
            src="/images/star.png"
            alt="star"
            width={20}
            height={20}
            className="mr-1 mb-[3px] text-y-gold drop-shadow-md"
          />
          {starScore}
        </span>
        <span className="flex justify-center items-center">
          {props.cardProps.nickName}
          <BiUser className="ml-1 bg-y-brown text-white rounded-full w-4 h-4" />
        </span>
      </div>
      {/* 태그, 설명 */}
      <div className="py-2 px-1 h-28 w-full line-clamp-3 border-y-2 border-gray-200 text-xs overflow-hidden relative">
        <SmallTag tags={props.cardProps.tags} />
        {props.cardProps.description === undefined ? (
          <div className="text-y-gray ">
            {noReviewState[randomNum]?.contents}
          </div>
        ) : props.cardProps?.description.length >= MAX_LEN ? (
          <>
            {/* 만약  MAX_LEN 보다 글자수가 많은 경우 더보기 보이기*/}
            <div className="text-xs leading-5">
              {props.cardProps.description}
            </div>
            <div className="absolute h-fit bottom-0 right-2 bg-white pl-1">
              ...
              <span className="text-y-gold ">더보기</span>
            </div>
          </>
        ) : (
          <div className="text-xs leading-5">{props.cardProps.description}</div>
        )}
      </div>
      {/* 날짜,코멘트수,엄지수 */}
      <div className="p-2 flex justify-between items-center text-[8px]">
        <div className="text-y-gray">{props.cardProps.date}</div>
        <div className="flex">
          <span className="flex justify-center">
            <FaRegCommentDots className="mr-[2px] mt-[1px] w-3 h-3" />
            {props.cardProps.comments}
          </span>
          <span className="ml-1 flex justify-center">
            <FiThumbsUp className="w-3 h-3 mt-[1px]" />
            {props.cardProps.thumbs}
          </span>
        </div>
      </div>
    </div>
  );
}
