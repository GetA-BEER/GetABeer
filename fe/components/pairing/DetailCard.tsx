import { MdModeEdit } from 'react-icons/md';
import { RiAlarmWarningFill } from 'react-icons/ri';
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
  const [TOKEN] = useRecoilState(accessToken);
  const config = {
    headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
    withCredentials: true,
  };

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
      text: '?????????????????????????',
      showCancelButton: true,
      confirmButtonColor: '#f1b31c',
      cancelButtonColor: '#A7A7A7',
      confirmButtonText: '??????',
      cancelButtonText: '??????',
    }).then((result) => {
      if (result.isConfirmed) {
        axios
          .delete(`/api/pairings/${curRoute}`, config)
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
      {/*?????????, ??????*/}
      <div className="flex justify-between items-center">
        <ProfileCard
          nickname={pairingProps?.nickname}
          date={date}
          userImage={pairingProps?.userImage}
          userId={pairingProps?.userId}
        />
        {pairingProps?.userId === curUserId ? (
          <div className="flex px-4">
            <div onClick={hadleEdit}>
              <MdModeEdit className="text-y-brown inline -mr-0.5" /> ??????
            </div>
            <div onClick={hadleDelte}>
              <HiTrash className="text-y-brown ml-1 inline" />
              <span>??????</span>
            </div>
          </div>
        ) : (
          <div className="flex-1 flex justify-end items-center  text-y-brown mr-3 text-xs">
            <button
              className="flex items-center mr-1"
              onClick={() => {
                // console.log('????????????');
              }}
            >
              <RiAlarmWarningFill className="mb-[1px]" />
              <span className="text-y-black ml-[1px]">????????????</span>
            </button>
          </div>
        )}
      </div>
      {/* ??????,?????? */}
      <div>
        <div className="w-full px-2">
          {pairingProps?.imageList === undefined ? (
            <></>
          ) : (
            <PairingImageCarousel imageList={pairingProps?.imageList} />
          )}
          <div className="p-2 h-fit overflow-hidden w-full leading-6">
            <div className="w-fit px-2 py-[2px] mb-2 text-xs md:text-sm rounded-md text-white bg-y-gold">
              {CategoryMatcherToKor(pairingProps?.category)}
            </div>
            {pairingProps?.content === undefined ? (
              <div className="text-y-gray text-xs md:text-sm">
                {noReviewState[randomNum]?.contents}
              </div>
            ) : (
              <div className="text-xs md:text-sm">{pairingProps?.content}</div>
            )}
          </div>
        </div>
      </div>

      {/* ????????????,????????? */}
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
