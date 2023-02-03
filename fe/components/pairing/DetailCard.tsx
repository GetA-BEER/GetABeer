import { FiThumbsUp } from 'react-icons/fi';
import { PairingInfo } from '@/pages/pairing/[id]';
import { MdModeEdit } from 'react-icons/md';
import { HiTrash } from 'react-icons/hi';
import ProfileCard from './ProfileCard';
import ImageCarouel from './ImageCarousel';
import { useRecoilValue } from 'recoil';
import { noReview } from '@/atoms/noReview';
import { NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';

export default function DetailCard(props: { pairingProps: PairingInfo }) {
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  return (
    <div className="rounded-lg bg-white text-y-black text-xs border-2 mx-2 mt-3">
      {/*닉네임, 날짜*/}
      <div className="flex justify-between items-center">
        <ProfileCard
          nickname={props.pairingProps.nickname}
          date={props.pairingProps.date}
        />
        <div className="flex px-4">
          <MdModeEdit className="text-y-brown" /> 수정
          <HiTrash className="text-y-brown ml-1" /> 삭제
        </div>
      </div>
      {/* 사진,설명 */}
      <div>
        <div className="w-full px-2">
          {props?.pairingProps?.image === undefined ? (
            <></>
          ) : (
            <ImageCarouel imageProps={props?.pairingProps?.image} />
          )}
          <div className="p-2 h-fit overflow-hidden w-full leading-6">
            <div className="w-fit px-2 py-[2px] text-xs rounded-md text-white bg-y-gold">
              {props.pairingProps.category}
            </div>
            {props.pairingProps.description === undefined ? (
              <div className="text-y-gray">
                {noReviewState[randomNum]?.contents}
              </div>
            ) : (
              <>{props.pairingProps.description}</>
            )}
          </div>
        </div>
      </div>

      {/* 코멘트수,엄지수 */}
      <div className="py-2 px-5 flex justify-end items-center text-[8px]">
        <FiThumbsUp className="w-3 h-3 mb-[3px]" />
        {props.pairingProps.thumb}
      </div>
    </div>
  );
}
