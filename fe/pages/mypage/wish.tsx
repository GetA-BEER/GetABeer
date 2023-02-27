import Head from 'next/head';
import WishCard from '@/components/wish/WishCard';
import { IoChevronBack } from 'react-icons/io5';
import Link from 'next/link';
import axios from '@/pages/api/axios';
import { useEffect, useState } from 'react';

export default function Wish() {
  const [wishList, setWishList] = useState<any>();
  const [pageInfo, setPageInfo] = useState();
  useEffect(() => {
    axios.get(`/api/mypage/wishlist`).then((response) => {
      setWishList(response.data.data);
      setPageInfo(response.data.pageInfo);
    });
  }, []);

  return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>

      <main className="m-auto h-screen mx-4">
        <Link href={'/mypage'}>
          <button className="m-4">
            <IoChevronBack className="w-6 h-6" />
          </button>
        </Link>
        <div className=" max-w-4xl m-auto">
          <div className="text-xl my-10 text-center font-semibold">
            유미님의 위시 맥주
          </div>
          <div className="grid grid-cols-2 gap-3">
            {wishList?.map((wishProps: any, idx: number) => (
              <WishCard key={idx} wishProps={wishProps} idx={idx + 1} />
            ))}
          </div>
          <div className="pb-14"></div>
        </div>
      </main>
    </>
  );
}
