import PageContainer from '@/components/PageContainer';
import BackBtn from '@/components/button/BackPageBtn';
import Link from 'next/link';
import Image from 'next/image';
import { useState, useEffect } from 'react';
import axios from '@/pages/api/axios';
import RatingCard, {
  RatingCardProps,
} from '@/components/middleCards/RatingCard';
import Pagenation from '@/components/Pagenation';
import { userNickname } from '@/atoms/login';
import { useRecoilState } from 'recoil';

export default function MyRating() {
  const [ratingList, setRatingList] = useState<RatingCardProps[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [userName] = useRecoilState(userNickname);

  useEffect(() => {
    axios
      .get('/api/mypage/ratings')
      .then((res) => {
        setRatingList(res.data.data);
        setTotalPages(res.data.pageInfo.totalPages);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <PageContainer>
      <main className="px-2">
        <BackBtn />
        <div className="flex justify-center my-4">
          <h1 className="text-xl lg:text-2xl font-bold">
            <span className="text-y-brown">{userName}님</span>의 평가
          </h1>
        </div>
        <div className="mt-3">
          {ratingList.map((el: RatingCardProps) => {
            return (
              <Link key={el.ratingId} href={`/rating/${el.ratingId}`}>
                <div className="border border-y-lightGray rounded-lg px-3 py-4">
                  <RatingCard
                    cardProps={el}
                    isMine={false}
                    count={el.commentCount}
                  />
                </div>
              </Link>
            );
          })}
        </div>
        {ratingList.length ? (
          <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
        ) : (
          <div className="noneContent py-8">
            <Image
              className="m-auto pb-3 opacity-50"
              src="/images/logo.png"
              alt="logo"
              width={40}
              height={40}
            />
            등록된 평가가 없습니다.
          </div>
        )}
      </main>
    </PageContainer>
  );
}
