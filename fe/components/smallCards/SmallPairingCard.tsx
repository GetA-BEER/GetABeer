import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
// import { PairingCardInfo } from './SmallpairingController';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';
import Image from 'next/image';
import { DetailTime } from '@/utils/TimeFunc';
import { CategoryMatcherToKor } from '@/utils/CategryMatcher';

export default function SmallPairingCard(props: { pairingProps: any }) {
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  const [collisions, setCollisions] = useState<boolean>(false);
  const [date, setDate] = useState<string>('');
  const initialDate = props?.pairingProps?.createdAt;

  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  useEffect(() => {
    if (initialDate !== undefined) {
      let tmpDate = DetailTime(initialDate);
      setDate(tmpDate);
    }
  }, [initialDate]);

  useEffect(() => {
    let parent = document.getElementById('pairingParents');
    let imageChild = document.getElementById(
      `pairingImage${props.pairingProps.pairingId}`
    );
    let deschild = document.getElementById(
      `pairingDescribe${props.pairingProps.pairingId}`
    );

    if (parent !== null && deschild !== null && imageChild !== null) {
      let parentHeight = parent.offsetHeight;
      let tagChildHeight = imageChild.clientHeight;
      let deschildHeight = deschild.clientHeight;
      if (parentHeight <= tagChildHeight + deschildHeight) setCollisions(true);
      console.log(parentHeight, tagChildHeight, deschildHeight, collisions);
    }
  }, [collisions, props.pairingProps.pairingId]);

  return (
    <div className="w-full ml-2 mb-2 rounded-lg bg-white text-y-black drop-shadow-lg text-xs border">
      {/* 페어링,닉네임 */}
      <div className="flex justify-between py-1 px-2">
        <span className="flex justify-center items-center px-3 py-[2px] rounded-md bg-y-gold text-white">
          {CategoryMatcherToKor(props.pairingProps.category)}
        </span>
        <span className="flex justify-center items-center">
          {props.pairingProps.nickname}
          <BiUser className="ml-1 bg-y-brown text-white rounded-full w-4 h-4" />
        </span>
      </div>
      {/* 사진,설명 */}
      <div
        className={`p-2 h-28 w-full border-y-2 border-gray-200 leading-5 relative ${
          collisions ? 'overflow-hidden' : ''
        }`}
        id="pairingParents"
      >
        {props.pairingProps.thumbnail === undefined ? (
          <div id={`pairingImage${props.pairingProps.pairingId}`}></div>
        ) : (
          <div className="h-[77px] overflow-hidden m-auto">
            <Image
              src={props.pairingProps.thumbnail}
              alt="img"
              width={100}
              height={77}
              className="m-auto"
              id={`pairingImage${props.pairingProps.pairingId}`}
            />
          </div>
        )}
        {props.pairingProps.content === undefined ? (
          <div
            className="text-y-gray"
            id={`pairingDescribe${props.pairingProps.pairingId}`}
          >
            {noReviewState[randomNum]?.contents}
          </div>
        ) : collisions ? (
          <>
            <div id={`pairingDescribe${props.pairingProps.pairingId}`}>
              {props.pairingProps.content}
            </div>
            <div className="absolute -bottom-[0.5px] right-1 px-1 bg-white">
              ...<span className="text-y-gold">더보기</span>
            </div>
          </>
        ) : (
          <div id={`pairingDescribe${props.pairingProps.pairingId}`}>
            {props.pairingProps.content}
          </div>
        )}
      </div>
      {/* 날짜,코멘트수,엄지수 */}
      <div className="p-2 flex justify-between items-center text-[8px]">
        <div className="text-y-gray">{date}</div>
        <div className="flex">
          <span className="flex justify-center">
            <FaRegCommentDots className="mr-[2px] mt-[1px] w-3 h-3" />
            {props.pairingProps.commentCount}
          </span>
          <span className="ml-1 flex justify-center">
            <FiThumbsUp className="w-3 h-3" />
            {props.pairingProps.likeCount}
          </span>
        </div>
      </div>
    </div>
  );
}
