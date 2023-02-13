import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { SmallCardInfo } from './SmallCardController';
import Image from 'next/image';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import SmallTag from '@/components/smallCards/SmallTag';

export default function SmallCard(props: {
  cardProps: SmallCardInfo;
  idx: number;
}) {
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [starScore, setStarScore] = useState<number | undefined>(
    props?.cardProps?.star
  );
  const [collisions, setCollisions] = useState<boolean>(false);
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

  useEffect(() => {
    let parent = document.getElementById('overParents');
    let tagChild = document.getElementById(`overTags${props.idx}`);
    let deschild = document.getElementById(`overDescribe${props.idx}`);

    if (parent !== null && deschild !== null && tagChild !== null) {
      let parentHeight = parent.offsetHeight;
      let tagChildHeight = tagChild.clientHeight;
      let deschildHeight = deschild.clientHeight;
      if (parentHeight <= tagChildHeight + deschildHeight) setCollisions(true);
      // console.log(parentHeight, tagChildHeight, deschildHeight, collisions);
    }
  }, [collisions, props.idx]);

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
      <div
        className={`py-2 px-1 h-28 w-full border-y-2 text-xs relative ${
          collisions ? 'overflow-hidden' : ''
        }`}
        id="overParents"
      >
        <div id={`overTags${props.idx}`}>
          <SmallTag tags={props.cardProps.tags} />
        </div>
        {props.cardProps.description === undefined ? (
          <div className="text-y-gray ">
            {noReviewState[randomNum]?.contents}
          </div>
        ) : collisions ? (
          <>
            <div
              className="text-xs leading-5 h-fit relative"
              id={`overDescribe${props.idx}`}
            >
              {props.cardProps.description}
            </div>
            <div className="absolute -bottom-[0.5px] right-1 px-1 bg-white">
              ...<span className="text-y-gold">더보기</span>
            </div>
          </>
        ) : (
          <div
            className="text-xs leading-5 h-fit"
            id={`overDescribe${props.idx}`}
          >
            {props.cardProps.description}
          </div>
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
