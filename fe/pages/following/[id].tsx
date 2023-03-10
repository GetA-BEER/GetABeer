import BackBtn from '@/components/button/BackPageBtn';
import FollowingUser, {
  FollowingProps,
} from '@/components/followPage/FollowingUser';
import Head from 'next/head';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';
import Image from 'next/image';
import Pagenation from '@/components/Pagenation';

export default function Following() {
  const router = useRouter();
  const userid = router.query.id;
  const [followingList, setFollowingList] = useState<FollowingProps[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  useEffect(() => {
    if (userid !== undefined) {
      axios
        .get(`/api/follows/${userid}/followings`)
        .then((res) => {
          console.log(res.data);
          setFollowingList(res.data.data);
          setPage(res.data.pageInfo.page);
          setTotalPages(res.data.pageInfo.totalPages);
        })
        .catch((error) => console.log(error));
    }
  }, [userid]);

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
          <div className="my-8 text-center text-xl bg-white font-semibold">
            팔로잉
          </div>
          {followingList.length === 0 ? (
            <div className="noneContent">
              <Image
                className="m-auto pb-3 opacity-50"
                src="/images/logo.png"
                alt="logo"
                width={40}
                height={40}
              />
              팔로잉이 없습니다.
            </div>
          ) : (
            <div className="m-2 border divide-y divide-gray-200 rounded-xl">
              {followingList.map((el: FollowingProps) => (
                <div key={el.userId}>
                  <FollowingUser followingProps={el} />
                </div>
              ))}
            </div>
          )}
        </div>
        {followingList.length ? (
          <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
        ) : null}
      </main>
    </>
  );
}