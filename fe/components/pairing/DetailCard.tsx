import { MdModeEdit } from 'react-icons/md';
import { HiTrash, HiOutlineChat } from 'react-icons/hi';
import ProfileCard from './ProfileCard';
import { useRecoilValue, useRecoilState } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';
import PairingImageCarousel from '@/components/pairing/PairingImageCarousel';
import { TimeHandler } from '@/utils/TimeHandler';
import { CategoryMatcherToKor } from '@/utils/CategryMatcher';
import { useRouter } from 'next/router';
import PairingThumbs from '../PairingThumbs';
import axios from '@/pages/api/axios';
import Swal from 'sweetalert2';
import { userId } from '@/atoms/login';
import { accessToken } from '@/atoms/login';

export default function DetailCard({ pairingProps, count }: any) {
  const [curUserId] = useRecoilState(userId);
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  let router = useRouter();
  const [curRoute, setCurRoute] = useState<any>();
  const [randomNum, setRandomNum] = useState(0);
  const [date, setDate] = useState<any>('');
  const [isLike, setIsLike] = useState<any>(pairingProps?.isUserLikes);
  const [likeCount, setLikeCount] = useState<any>(pairingProps?.likeCount);
  const initialDate = pairingProps?.createdAt;

  useEffect(() => {
    if (pairingProps !== undefined) {
      setCurRoute(router.query.id);
      setIsLike(pairingProps.isUserLikes);
      setLikeCount(pairingProps.likeCount);
    }
  }, [router, curRoute, pairingProps]);

  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);
  console.log(pairingProps);
  const TOKEN = useRecoilValue(accessToken);
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  useEffect(() => {
    if (initialDate !== undefined) {
      let tmpDate = TimeHandler(initialDate);
      setDate(tmpDate);
    }
  }, [initialDate]);

  const hadleDelte = () => {
    Swal.fire({
      text: '삭제하시겠습니까?',
      showCancelButton: true,
      confirmButtonColor: '#f1b31c',
      cancelButtonColor: '#A7A7A7',
      confirmButtonText: '삭제',
      cancelButtonText: '취소',
    }).then((result) => {
      if (result.isConfirmed) {
        axios
          .delete(`/api/pairings/${curRoute}`)
          .then(() => {
            router.back();
          })
          .catch((error) => console.log(error));
      }
    });
  };
  const hadleEdit = () => {
    router.replace(`/editpairing/${curRoute}`);
  };

  return (
    <>
      {/*닉네임, 날짜*/}
      <div className="flex justify-between items-center">
        <ProfileCard
          nickname={pairingProps?.nickname}
          date={date}
          userImage={pairingProps?.userImage}
        />
        {pairingProps?.userId === curUserId ? (
          <div className="flex px-4">
            <div onClick={hadleEdit}>
              <MdModeEdit className="text-y-brown inline" /> 수정
            </div>
            <div onClick={hadleDelte}>
              <HiTrash className="text-y-brown ml-1 inline" />
              <span>삭제</span>
            </div>
          </div>
        ) : (
          <></>
        )}
      </div>
      {/* 사진,설명 */}
      <div>
        <div className="w-full px-2">
          {pairingProps?.imageList === undefined ? (
            <></>
          ) : (
            <PairingImageCarousel imageList={pairingProps?.imageList} />
          )}
          <div className="p-2 h-fit overflow-hidden w-full leading-6">
            <div className="w-fit px-2 py-[2px] text-xs rounded-md text-white bg-y-gold">
              {CategoryMatcherToKor(pairingProps?.category)}
            </div>
            {pairingProps?.content === undefined ? (
              <div className="text-y-gray">
                {noReviewState[randomNum]?.contents}
              </div>
            ) : (
              <>{pairingProps?.content}</>
            )}
          </div>
        </div>
      </div>

      {/* 코멘트수,엄지수 */}
      <div className="py-2 px-5 flex justify-end items-center text-[8px]">
        <div className="flex justify-center items-center">
          <HiOutlineChat className="w-4 h-4" />
          <span className="text-[8px] ml-0.5">{count}</span>
        </div>
        <PairingThumbs
          isLogin={isLogin}
          pairingId={pairingProps?.pairingId}
          isLike={isLike}
          setIsLike={setIsLike}
          likeCount={likeCount}
          setLikeCount={setLikeCount}
        />
      </div>
    </>
  );
}
