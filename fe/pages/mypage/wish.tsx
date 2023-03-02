import Head from 'next/head';
import WishCard from '@/components/wish/WishCard';
import { IoChevronBack } from 'react-icons/io5';
import { useRecoilState } from 'recoil';
import { accessToken, userNickname } from '@/atoms/login';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';
import { useEffect, useState } from 'react';
import Image from 'next/image';
import Pagenation from '@/components/Pagenation';

export default function Wish() {
  const [wishList, setWishList] = useState<any>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [TOKEN] = useRecoilState(accessToken);
  const [username] = useRecoilState(userNickname);
  const router = useRouter();
  useEffect(() => {
    if (TOKEN === '') {
      router.push('/');
    }
  }, [TOKEN, router]);
  //.get(`/api/ratings/page/mostlikes?beerId=${curRoute}&page=1&size=5`) 페이지네이션이 잘 되어 있는건가요?
  useEffect(() => {
    // 그럼 페이지 부분 입력이 있으면 어떻게 넣어야 하는건지,..? /api/mypage/wishlist/page/?page=1&size=10
    axios.get(`/api/mypage/wishlist?&page=${page}`).then((response) => {
      setWishList(response.data.data);
      setTotalPages(response.data.pageInfo.totalPages);
      console.log(response.data.pageInfo);
    });
  }, [page]);

  return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>

      <main className="m-auto h-screen max-w-4xl">
        <button
          type="button"
          onClick={() => {
            router.back();
          }}
          className="ml-4 absolute"
        >
          <IoChevronBack className="w-6 h-6" />
        </button>
        <div className=" max-w-4xl m-auto">
          <div className="text-xl mb-10 text-center font-semibold break-keep">
            <span className="text-y-brown">{username}님</span>의 위시 맥주
          </div>

          {wishList.length === 0 ? (
            <div className="noneContent py-32">
              <Image
                className="m-auto pb-3 opacity-50"
                src="/images/logo.png"
                alt="logo"
                width={40}
                height={40}
              />
              등록된 위시 맥주가 없습니다.
            </div>
          ) : (
            <div className="grid grid-cols-2 gap-3">
              {wishList?.map((wishProps: any, idx: number) => (
                <WishCard key={idx} wishProps={wishProps} idx={idx + 1} />
              ))}
            </div>
          )}
          <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
          <div className="pb-14"></div>
        </div>
      </main>
    </>
  );
}
