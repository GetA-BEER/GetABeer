import { FiThumbsUp } from 'react-icons/fi';
// import { PairingInfo } from '@/pages/pairing/[id]';
import { MdModeEdit } from 'react-icons/md';
import { HiTrash } from 'react-icons/hi';
import ProfileCard from './ProfileCard';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';
import PairingImageCarousel from '@/components/pairing/PairingImageCarousel';

export default function DetailCard(props: { pairingProps: any }) {
  // const pairingProps: any = {
  //   beerId: 1,
  //   pairingId: 1,
  //   userId: 1,
  //   nickname: '닉네임1',
  //   content: '페어링 안내',
  //   imageList: [
  //     {
  //       pairingImageId: 1,
  //       imageUrl:
  //         'https://getabeer.s3.ap-northeast-2.amazonaws.com/image/2023-02-09-13-57-53-233/pairing_images_1_1.png',
  //       fileName: 'image/2023-02-09-13-57-53-233/pairing_images_1_1.png',
  //     },
  //     {
  //       pairingImageId: 2,
  //       imageUrl:
  //         'https://getabeer.s3.ap-northeast-2.amazonaws.com/image/2023-02-09-13-57-53-713/pairing_images_1_1.png',
  //       fileName: 'image/2023-02-09-13-57-53-713/pairing_images_1_1.png',
  //     },
  //   ],
  //   commentList: [
  //     {
  //       pairingId: 1,
  //       pairingCommentId: 1,
  //       userId: 1,
  //       nickname: '닉네임1',
  //       content: '페어링 댓글',
  //       createdAt: '2023-02-09T13:58:20.330872',
  //       modifiedAt: '2023-02-09T13:58:20.330872',
  //     },
  //     {
  //       pairingId: 1,
  //       pairingCommentId: 2,
  //       userId: 1,
  //       nickname: '닉네임1',
  //       content: '페어링 댓글',
  //       createdAt: '2023-02-09T13:58:23.619436',
  //       modifiedAt: '2023-02-09T13:58:23.619436',
  //     },
  //   ],
  //   category: 'GRILL',
  //   likeCount: 0,
  //   commentCount: 2,
  //   createdAt: '2023-02-09T13:57:53.875197',
  //   modifiedAt: '2023-02-09T13:58:23.621731',
  // };

  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  return (
    <div className="rounded-lg bg-white text-y-black text-xs border-2 mx-2">
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
            <PairingImageCarousel />
          )}
          <div className="p-2 h-fit overflow-hidden w-full leading-6">
            <div className="w-fit px-2 py-[2px] text-xs rounded-md text-white bg-y-gold">
              {props.pairingProps.category}
            </div>
            {props.pairingProps.content === undefined ? (
              <div className="text-y-gray">
                {noReviewState[randomNum]?.contents}
              </div>
            ) : (
              <>{props.pairingProps.content}</>
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
