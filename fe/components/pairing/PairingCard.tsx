import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { PairingCardInfo } from './PairingCardController';
import ProfileCard from './ProfileCard';
import Image from 'next/image';
import { useRecoilValue } from 'recoil';
import { noReview } from '@/atoms/noReview';
import { NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';

export default function PairingCard(props: {
  pairingCardProps: PairingCardInfo;
  idx: number;
}) {
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  const [collisions, setCollisions] = useState<boolean>(false);
  const MAX_PARENT_HEIGHT = 96;
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  useEffect(() => {
    let myDesc = document.getElementById(`myDescribe${props.idx}`);
    if (myDesc !== null) {
      let myDescHeight = myDesc.offsetHeight;
      if (MAX_PARENT_HEIGHT < myDescHeight) setCollisions(true);
      // console.log(parentHeight, tagChildHeight, deschildHeight, collisions);
    }
  }, [collisions, props.idx]);

  return (
    <div className="rounded-lg bg-white text-y-black text-xs border-2 mx-2 mt-3">
      {/*닉네임, 날짜*/}
      <ProfileCard
        nickname={props.pairingCardProps.nickname}
        date={props.pairingCardProps.date}
      />
      {/* 사진,설명 */}

      <div className="grid grid-cols-3 gap-3 px-3 h-24">
        {props?.pairingCardProps?.image === undefined ? (
          <div className="col-span-3 h-24 overflow-hidden w-full leading-6 relative">
            {props?.pairingCardProps?.description === undefined ? (
              <div className="text-y-gray">
                {noReviewState[randomNum]?.contents}
              </div>
            ) : collisions ? (
              <>
                <div
                  className="leading-6 h-fit relative"
                  id={`myDescribe${props.idx}`}
                >
                  {props.pairingCardProps.description}
                </div>
                <div className="absolute bottom-[1.1px] right-0 px-2 bg-white">
                  ...<span className="text-y-gold">더보기</span>
                </div>
              </>
            ) : (
              <div
                className="text-xs leading-6 h-fit"
                id={`myDescribe${props.idx}`}
              >
                {props.pairingCardProps.description}
              </div>
            )}
          </div>
        ) : (
          <>
            <div className="h-24 flex bg-auto overflow-hidden border rounded-lg">
              <Image
                src={props?.pairingCardProps?.image}
                className="m-auto w-full"
                alt="star"
                width={180}
                height={200}
              />
            </div>
            <div className="col-span-2 h-24 overflow-hidden w-full leading-6 relative">
              {props?.pairingCardProps?.description === undefined ? (
                <div className="text-y-gray">
                  {noReviewState[randomNum]?.contents}
                </div>
              ) : collisions ? (
                <>
                  <div
                    className="leading-6 h-fit relative"
                    id={`myDescribe${props.idx}`}
                  >
                    {props.pairingCardProps.description}
                  </div>
                  <div className="absolute bottom-[1.1px] right-0 px-2 bg-white">
                    ...<span className="text-y-gold">더보기</span>
                  </div>
                </>
              ) : (
                <div
                  className="text-xs leading-6 h-fit"
                  id={`myDescribe${props.idx}`}
                >
                  {props.pairingCardProps.description}
                </div>
              )}
            </div>
          </>
        )}
      </div>
      {/* 코멘트수,엄지수 */}
      <div className="py-2 px-2 flex justify-end items-center text-[8px]">
        <div className="flex">
          <span className="flex justify-center">
            <FaRegCommentDots className="mr-1 mt-[1px] w-3 h-3" />
            {props.pairingCardProps.comment}
          </span>
          <span className="mx-2 flex justify-center">
            <FiThumbsUp className="w-3 h-3 mt-[1px]" />
            {props.pairingCardProps.thumb}
          </span>
        </div>
      </div>
    </div>
  );
}
