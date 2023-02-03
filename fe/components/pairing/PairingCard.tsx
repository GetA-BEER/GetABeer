import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { PairingCardInfo } from './PairingCardController';
import ProfileCard from './ProfileCard';
import Image from 'next/image';

export default function PairingCard(props: {
  pairingCardProps: PairingCardInfo;
}) {
  return (
    <div className="rounded-lg bg-white text-y-black text-xs border-2 mx-2 mt-3">
      {/*닉네임, 날짜*/}
      <ProfileCard
        nickname={props.pairingCardProps.nickname}
        date={props.pairingCardProps.date}
      />
      {/* 사진,설명 */}
      <div>
        <div className="flex items-center px-2">
          {props?.pairingCardProps?.image === undefined ? (
            <></>
          ) : (
            <Image
              src={props?.pairingCardProps?.image}
              alt="star"
              width={100}
              height={100}
              className="mx-2 border"
            />
          )}
          <div className="p-2 h-28 overflow-hidden w-full leading-6">
            {props.pairingCardProps.description}.....
            <span className="text-y-gold">더보기</span>
          </div>
        </div>
      </div>
      {/* 코멘트수,엄지수 */}
      <div className="py-2 px-5 flex justify-end items-center text-[8px]">
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
