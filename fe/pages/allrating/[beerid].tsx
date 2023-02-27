import RatingCard, {
  RatingCardProps,
} from '@/components/middleCards/RatingCard';
import SortBox, { Sort } from '@/components/selectBox/SortBox';
import axios from '@/pages/api/axios';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { IoChevronBack } from 'react-icons/io5';
import PageContainer from '@/components/PageContainer';
import Pagenation from '@/components/Pagenation';
import Image from 'next/image';

export default function AllRating() {
  const router = useRouter();
  const beerId = router.query.beerid;
  const [title, setTitle] = useState<string>('');
  const [sort, setSort] = useState<Sort>('mostlikes');
  const [ratingList, setRatingList] = useState<RatingCardProps[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  useEffect(() => {
    if (beerId !== undefined) {
      axios
        .get(`/api/ratings/page/${sort}?beerId=${beerId}&page=${page}&size=5`)
        .then((res) => {
          setRatingList(res.data.data);
          setTotalPages(res.data.pageInfo.totalPages);
          setTitle(res.data.pageInfo.beerKorName);
        })
        .catch((err) => console.log(err));
    }
  }, [beerId, sort, page]);

  return (
    <PageContainer>
      <main className="px-2">
        <IoChevronBack
          onClick={() => router.back()}
          className="text-xl text-y-gray mt-2"
        />
        <div className="flex justify-center my-4">
          <h1 className="text-xl lg:text-2xl font-bold">{title}</h1>
        </div>
        <SortBox setSort={setSort} />
        <div className="mt-3">
          {ratingList.map((el: RatingCardProps) => {
            return (
              <Link key={el.ratingId} href={`/rating/${el.ratingId}`}>
                <div className="border border-y-lightGray rounded-lg px-3 py-4 m-2">
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
          <div className="flex flex-col justify-center items-center rounded-lg bg-y-lightGray py-5">
            <Image
              className="m-auto pb-3 opacity-50"
              src="/images/logo.png"
              alt="logo"
              width={40}
              height={40}
            />
            <span>등록된 평가가 없습니다</span>
          </div>
        )}
      </main>
    </PageContainer>
  );
}
