import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { PairingCardInfo } from './SmallpairingController';
import { useRecoilValue } from 'recoil';
import { noReview } from '@/atoms/noReview';
import { NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';

export default function SmallPairingCard(props: {
  pairingProps: PairingCardInfo;
}) {
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  return (
    <div className="w-full rounded-lg bg-white text-y-black drop-shadow-lg text-xs border">
      {/* 페어링,닉네임 */}
      <div className="flex justify-between py-1 px-2">
        <span className="flex justify-center items-center px-3 py-[2px] rounded-md bg-y-gold text-white">
          {props.pairingProps.pairing}
        </span>
        <span className="flex justify-center items-center">
          {props.pairingProps.nickName}
          <BiUser className="ml-1 bg-y-brown text-white rounded-full w-4 h-4" />
        </span>
      </div>
      {/* 설명 */}
      <div className="p-2 h-28 overflow-hidden w-full border-y-2 border-gray-200 leading-6">
        {props.pairingProps.description === undefined ? (
          <div className="text-y-gray">
            {noReviewState[randomNum]?.contents}
          </div>
        ) : (
          <>
            {props.pairingProps.description}...
            <span className="text-y-gold">더보기</span>
          </>
        )}
      </div>
      {/* 날짜,코멘트수,엄지수 */}
      <div className="p-2 flex justify-between items-center text-[8px]">
        <div className="text-y-gray">{props.pairingProps.date}</div>
        <div className="flex">
          <span className="flex justify-center">
            <FaRegCommentDots className="mr-[2px] mt-[1px] w-3 h-3" />
            {props.pairingProps.comments}
          </span>
          <span className="ml-1 flex justify-center">
            <FiThumbsUp className="w-3 h-3 mt-[1px]" />
            {props.pairingProps.thumbs}
          </span>
        </div>
      </div>
    </div>
  );
}
