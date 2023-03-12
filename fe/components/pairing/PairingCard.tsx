import Link from 'next/link';
import Image from 'next/image';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';
import { HiOutlineChat } from 'react-icons/hi';
import { FaThumbsUp, FaRegThumbsUp } from 'react-icons/fa';
import axios from '@/pages/api/axios';
import { useRouter } from 'next/router';
import { accessToken } from '@/atoms/login';
import Swal from 'sweetalert2';
import { PairingCardProps } from '../beerPage/BeerDeclare';
import { useRecoilState } from 'recoil';

export default function PairingCard(props: { pairingCardProps: any }) {
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  const [collisions, setCollisions] = useState<boolean>(false);
  const [isLike, setIsLike] = useState<boolean>(
    props.pairingCardProps.isUserLikes
  );
  const [likeCount, setLikeCount] = useState<number>(
    props.pairingCardProps.likeCount
  );
  const router = useRouter();
  const [TOKEN] = useRecoilState(accessToken);
  const config = {
    headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
    withCredentials: true,
  };
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  const goToLogin = () => {
    Swal.fire({
      text: '로그인이 필요한 서비스 입니다.',
      showCancelButton: true,
      confirmButtonColor: '#f1b31c',
      cancelButtonColor: '#A7A7A7',
      confirmButtonText: '로그인',
      cancelButtonText: '취소',
    }).then((result) => {
      if (result.isConfirmed) {
        router.push({
          pathname: '/login',
        });
      }
    });
  };

  const MAX_PARENT_HEIGHT = 96;
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  useEffect(() => {
    let myDesc = document.getElementById(
      `myDescribe${props.pairingCardProps.pairingId}`
    );
    if (myDesc !== null) {
      let myDescHeight = myDesc.offsetHeight;
      if (MAX_PARENT_HEIGHT < myDescHeight) setCollisions(true);
    }
  }, [collisions, props.pairingCardProps.pairingId]);

  useEffect(() => {
    if (props.pairingCardProps !== null) {
      setLikeCount(props.pairingCardProps.likeCount);
      setIsLike(props.pairingCardProps.isUserLikes);
    }
  }, [props]);

  const isUserLikeHandler = () => {
    if (isLogin) {
      axios
        .post(
          `/api/pairings/likes?pairingId=${props?.pairingCardProps?.pairingId}`,
          {},
          config
        )
        .then((response) => {
          setIsLike(!isLike);
          if (isLike) {
            setLikeCount(likeCount - 1);
          } else {
            setLikeCount(likeCount + 1);
          }
        });
    } else {
      goToLogin();
    }
  };

  return (
    <>
      <Link href={`/pairing/${props.pairingCardProps.pairingId}`}>
        <div className="grid grid-cols-3 gap-3 px-3 h-24">
          {props?.pairingCardProps?.thumbnail === '' ? (
            <div className="col-span-3 h-24 overflow-hidden w-full leading-6 relative">
              {props?.pairingCardProps?.content === undefined ? (
                <div className="text-y-gray">
                  {noReviewState[randomNum]?.contents}
                </div>
              ) : collisions ? (
                <>
                  <div
                    className="leading-6 h-fit relative"
                    id={`myDescribe${props?.pairingCardProps?.pairingId}`}
                  >
                    {props?.pairingCardProps?.content}
                  </div>
                  <div className="absolute bottom-[1.1px] right-0 px-2 bg-white">
                    ...<span className="text-y-gold">더보기</span>
                  </div>
                </>
              ) : (
                <div
                  className="text-xs leading-6 h-fit px-2"
                  id={`myDescribe${props?.pairingCardProps?.pairingId}`}
                >
                  {props?.pairingCardProps?.content}
                </div>
              )}
            </div>
          ) : (
            <>
              <div className="h-24 flex bg-auto overflow-hidden border rounded-lg">
                {props?.pairingCardProps?.thumbnail === '' ||
                props?.pairingCardProps?.thumbnail === null ? (
                  <div>사진 준비중</div>
                ) : (
                  <Image
                    src={props?.pairingCardProps?.thumbnail}
                    className="m-auto h-full w-auto select-none"
                    alt="star"
                    width={180}
                    height={200}
                    priority
                  />
                )}
              </div>
              <div className="col-span-2 h-24 overflow-hidden w-full leading-6 relative">
                {props?.pairingCardProps?.content === undefined ? (
                  <div className="text-y-gray">
                    {noReviewState[randomNum]?.contents}
                  </div>
                ) : collisions ? (
                  <>
                    <div
                      className="leading-6 h-fit relative"
                      id={`myDescribe${props?.pairingCardProps?.pairingId}`}
                    >
                      {props?.pairingCardProps?.content}
                    </div>
                    <div className="absolute bottom-[1.1px] right-0 px-2 bg-white">
                      ...<span className="text-y-gold">더보기</span>
                    </div>
                  </>
                ) : (
                  <div
                    className="text-xs leading-6 h-fit"
                    id={`myDescribe${props?.pairingCardProps?.pairingId}`}
                  >
                    {props?.pairingCardProps?.content}
                  </div>
                )}
              </div>
            </>
          )}
        </div>
      </Link>
      {/* 코멘트수,엄지수 */}
      <div className="flex justify-end mr-3 mb-3">
        <div className="flex justify-center items-center">
          <HiOutlineChat />
          <span className="text-xs ml-0.5 mr-2 mt-0.5">
            {props?.pairingCardProps?.commentCount}
          </span>
        </div>
        <button
          className="flex justify-center items-center"
          onClick={isUserLikeHandler}
        >
          <span>{isLike ? <FaThumbsUp /> : <FaRegThumbsUp />}</span>
          <span className="text-xs ml-0.5 mr-0.5 mt-0.5">{likeCount}</span>
        </button>
      </div>
    </>
  );
}
