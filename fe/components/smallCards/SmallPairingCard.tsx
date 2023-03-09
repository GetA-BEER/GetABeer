import Image from 'next/image';
import Link from 'next/link';
import { FaRegCommentDots } from 'react-icons/fa';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';
import { TimeHandler } from '@/utils/TimeHandler';
import { CategoryMatcherToKor } from '@/utils/CategryMatcher';
import PairingThumbs from '../PairingThumbs';
import { accessToken, userId } from '@/atoms/login';
import { useRouter } from 'next/router';

export default function SmallPairingCard({ pairingProps }: any) {
  const [pairingList, setpairingList] = useState<any>([]);
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  const [collisions, setCollisions] = useState<boolean>(false);
  const [date, setDate] = useState<any>('');
  const initialDate = pairingProps?.createdAt;
  const [isLike, setIsLike] = useState<any>(pairingProps.isUserLikes);
  const [likeCount, setLikeCount] = useState<any>(pairingProps.likeCount);
  const TOKEN = useRecoilValue(accessToken);
  const USERID = useRecoilValue(userId);
  const [isLogin, setIsLogin] = useState(false);
  const router = useRouter();
  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  // 페어링 리스트 초기화
  useEffect(() => {
    if (pairingProps !== undefined) {
      setpairingList(pairingProps);
      setLikeCount(pairingProps.likeCount);
      setIsLike(pairingProps.isUserLikes);
    }
  }, [pairingProps]);

  // 글이 없는 경우
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  useEffect(() => {
    if (initialDate !== undefined) {
      let tmpDate = TimeHandler(initialDate);
      setDate(tmpDate);
    }
  }, [initialDate]);

  // 더보기 길이 계산
  useEffect(() => {
    if (pairingList !== undefined) {
      let parent = document.getElementById('pairingParents');
      let imageChild = document.getElementById(
        `pairingImage${pairingList.pairingId}`
      );
      let deschild = document.getElementById(
        `pairingDescribe${pairingList.pairingId}`
      );

      if (parent !== null && deschild !== null && imageChild !== null) {
        let parentHeight = parent.offsetHeight;
        let tagChildHeight = imageChild.clientHeight;
        let deschildHeight = deschild.clientHeight;
        if (parentHeight <= tagChildHeight + deschildHeight)
          setCollisions(true);
      }
    }
  }, [collisions, pairingList]);
  const userCheck = () => {
    if (USERID !== pairingList?.userId) {
      router.push(`/userpage/${pairingList?.userId}`);
    } else {
      router.push(`/mypage`);
    }
  };
  return (
    <>
      <div className="w-full ml-2 mb-2 rounded-lg bg-white text-y-black drop-shadow-lg text-[8px] border">
        {/* 페어링,닉네임 */}
        <div className="flex justify-between py-1 px-2">
          <span className="flex justify-center items-center px-2 py-[2px] rounded-md bg-y-gold text-white">
            {CategoryMatcherToKor(pairingList?.category)}
          </span>
          <span
            className="flex justify-end items-center w-2/5"
            onClick={userCheck}
          >
            <span className="w-[70%] text-end truncate pr-[2px]">
              {pairingList?.nickname}
            </span>
            {pairingList.userImage ? (
              <Image
                alt="userImg"
                src={pairingList?.userImage}
                width={100}
                height={100}
                className="w-4 h-4 rounded-full"
                priority
              />
            ) : (
              <></>
            )}
          </span>
        </div>
        {/* 사진,설명 */}
        <Link href={`/pairing/${pairingList?.pairingId}`}>
          <div
            className={`p-2 h-28 w-full border-y-2 border-gray-200 leading-5 relative ${
              collisions ? 'overflow-hidden' : ''
            }`}
            id="pairingParents"
          >
            {pairingList?.thumbnail === '' ||
            pairingList?.thumbnail === null ? (
              <div id={`pairingImage${pairingList?.pairingId}`}></div>
            ) : (
              <div className="h-[77px] w-auto overflow-hidden">
                <Image
                  src={pairingList?.thumbnail}
                  alt="img"
                  width={100}
                  height={100}
                  className="m-auto h-full w-auto select-none"
                  id={`pairingImage${pairingList?.pairingId}`}
                  priority
                />
              </div>
            )}
            {pairingList?.content === undefined ? (
              <div
                className="text-y-gray text-[8px]"
                id={`pairingDescribe${pairingList?.pairingId}`}
              >
                {noReviewState[randomNum]?.contents}
              </div>
            ) : collisions ? (
              <>
                <div
                  id={`pairingDescribe${pairingList?.pairingId}`}
                  className="text-[8px]"
                >
                  {pairingList?.content}
                </div>
                <div className="absolute bottom-0.5 right-1 px-1 bg-white">
                  ...<span className="text-y-gold">더보기</span>
                </div>
              </>
            ) : (
              <div
                id={`pairingDescribe${pairingList?.pairingId}`}
                className="text-[8px]"
              >
                {pairingList?.content}
              </div>
            )}
          </div>
        </Link>
        {/* 날짜,코멘트수,엄지수 */}
        <div className="p-2 flex justify-between items-center text-[8px]">
          <div className="text-y-gray">{date}</div>
          <div className="flex">
            <span className="flex justify-center">
              <FaRegCommentDots className="mr-[2px] mt-[1px] w-3 h-3" />
              {pairingList?.commentCount}
            </span>
            <PairingThumbs
              isLogin={isLogin}
              pairingId={pairingList.pairingId}
              isLike={isLike}
              setIsLike={setIsLike}
              likeCount={likeCount}
              setLikeCount={setLikeCount}
            />
          </div>
        </div>
      </div>
    </>
  );
}
