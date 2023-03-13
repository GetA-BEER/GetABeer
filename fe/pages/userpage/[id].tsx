import BackBtn from '@/components/button/BackPageBtn';
import SubmitBtn from '@/components/button/SubmitBtn';
import RatingCard, {
  RatingCardProps,
} from '@/components/middleCards/RatingCard';
import PairingCardController from '@/components/pairing/PairingCardController';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';
import Head from 'next/head';
import { useEffect, useState } from 'react';
import { accessToken } from '@/atoms/login';
import { useRecoilState } from 'recoil';
import Link from 'next/link';
import Image from 'next/image';
import CloseBtn from '@/components/button/CloseBtn';
import Swal from 'sweetalert2';
import Pagenation from '@/components/Pagenation';

export default function UserPage() {
  const [pariginCardPops, setPairingCardProps] = useState<any>();
  const [ratingList, setRatingList] = useState<RatingCardProps[]>([]);
  const [userName, setUserName] = useState();
  const [paringCount, setParingCount] = useState();
  const [ratingCount, setRatingCount] = useState();
  const [commentCount, setCommentCount] = useState();
  const [followingCount, setFollowingCount] = useState(0);
  const [followerCount, setFollowerCount] = useState(0);
  const [follow, setFollow] = useState(false);
  const [userImg, setUserImg] = useState('');
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [TOKEN] = useRecoilState(accessToken);
  const [isLogin, setIsLogin] = useState(false);
  const router = useRouter();
  const { id } = router.query;

  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  const goToLogin = () => {
    Swal.fire({
      title: 'Get A Beer',
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

  useEffect(() => {
    const config = {
      headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
      withCredentials: true,
    };
    if (id !== undefined) {
      axios
        .get(`/api/user/${id}`, config)
        .then((res) => {
          setUserName(res.data.nickname);
          setParingCount(res.data.pairingCount);
          setRatingCount(res.data.ratingCount);
          setCommentCount(res.data.commentCount);
          setFollowerCount(res.data.followerCount);
          setFollowingCount(res.data.followingCount);
          setUserImg(res.data.imgUrl);
          setFollow(res.data.isFollowing);
        })
        .catch((error) => console.log(error));
    }
  }, [TOKEN, id]);
  useEffect(() => {
    const config = {
      headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
      withCredentials: true,
    };

    axios
      .get(`/api/user/${id}/pairings?page=${page}`, config)
      .then((res) => {
        setPairingCardProps(res.data.data);
        setTotalPages(res.data.pageInfo.totalPages);
      })
      .catch((error) => console.log(error));

    axios
      .get(`/api/user/${id}/ratings?page=${page}`, config)
      .then((res) => {
        setRatingList(res.data.data);
        setTotalPages(res.data.pageInfo.totalPages);
      })
      .catch((err) => console.log(err));
  }, [TOKEN, id, page]);

  const [curTab, setCurTab] = useState(0);
  const tabArr = [
    {
      name: '평가',
      content: (
        <div className="mt-3">
          {ratingList.length === 0 ? (
            <div className="noneContent">
              <Image
                className="m-auto pb-3 opacity-50"
                src="/images/logo.png"
                alt="logo"
                width={40}
                height={40}
              />
              등록된 평가가 없습니다.
            </div>
          ) : (
            <div className="mt-3">
              {ratingList.map((el: RatingCardProps) => {
                return (
                  <Link key={el.ratingId} href={`/rating/${el.ratingId}`}>
                    <div className="border border-y-lightGray rounded-lg px-3 py-4 m-2">
                      <RatingCard cardProps={el} count={el.commentCount} />
                    </div>
                  </Link>
                );
              })}
            </div>
          )}
          {ratingList?.length ? (
            <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
          ) : null}
        </div>
      ),
    },
    {
      name: '페어링',
      content: (
        <div>
          {pariginCardPops?.length ? (
            <PairingCardController pairingCardProps={pariginCardPops} />
          ) : (
            <div className="noneContent">
              <Image
                className="m-auto pb-3 opacity-50"
                src="/images/logo.png"
                alt="logo"
                width={40}
                height={40}
              />
              등록된 페어링이 없습니다.
            </div>
          )}{' '}
          {pariginCardPops?.length ? (
            <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
          ) : null}
        </div>
      ),
    },
  ];
  const followClick = () => {
    const config = {
      headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
      withCredentials: true,
    };
    axios
      .post(`/api/follows/${id}`, {}, config)
      .then((res) => {
        if (res.data === 'Create Follow') {
          setFollow(true);
          setFollowerCount(followerCount + 1);
        } else {
          setFollow(false);
          setFollowerCount(followerCount - 1);
        }
      })
      .catch((err) => {
        console.log(err);
        goToLogin();
      });
  };
  return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>
      <main className="m-auto h-screen max-w-md">
        <BackBtn></BackBtn>
        <div>
          <div className="flex gap-3 p-2 justify-center">
            <div className="relative rounded-full w-20 h-20 self-center">
              {userImg ? (
                <Image
                  alt=" user profile image"
                  src={userImg}
                  fill
                  sizes="100vw"
                  className="object-cover rounded-full"
                />
              ) : (
                <></>
              )}
            </div>
            <div className="p-1 self-center">
              <div>{userName}님</div>
              <div className="text-xs mb-2">
                평가{ratingCount} 페어링{paringCount} 댓글{commentCount}
              </div>
              <div className="flex gap-2">
                <div
                  className="bg-gray-300 text-center py-2 w-16 rounded-lg text-xs cursor-pointer"
                  onClick={() =>
                    router.push({
                      pathname: `/follower/${id}`,
                    })
                  }
                >
                  {followerCount} 팔로워
                </div>
                <div
                  className="bg-gray-300 text-center py-2 w-16 rounded-lg text-xs cursor-pointer"
                  onClick={() =>
                    router.push({
                      pathname: `/following/${id}`,
                    })
                  }
                >
                  {followingCount} 팔로잉
                </div>
              </div>
            </div>
          </div>

          {follow === null || follow === false ? (
            <SubmitBtn onClick={followClick}>팔로우</SubmitBtn>
          ) : (
            <CloseBtn onClick={followClick}>팔로잉</CloseBtn>
          )}
        </div>
        <div className="border rounded-lg m-1 p-2">
          <ul className="flex justify-around mt-1 mb-4">
            {tabArr.map((el, idx) => {
              return (
                <li
                  key={idx}
                  className={
                    curTab === idx
                      ? 'text-y-brown border-b-2 border-y-brown px-2 cursor-pointer'
                      : 'text-y-gray px-2 cursor-pointer'
                  }
                  onClick={() => setCurTab(idx)}
                >
                  {el.name}
                </li>
              );
            })}
          </ul>
          {tabArr[curTab].content}
        </div>
        <div className="pb-20"></div>
      </main>
    </>
  );
}
