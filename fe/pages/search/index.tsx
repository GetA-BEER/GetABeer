import PageContainer from '@/components/PageContainer';
import { IoChevronBack } from 'react-icons/io5';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import SearchCard, {
  SearchCardProps,
} from '@/components/middleCards/SearchCard';
import axios from '@/pages/api/axios';
import Link from 'next/link';
import Image from 'next/image';
import Pagenation from '@/components/Pagenation';
import { SearchMatcherToKor } from '@/utils/SearchMatcher';

export default function Search() {
  const router = useRouter();
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [searchResultList, setSearchResultList] = useState<SearchCardProps[]>(
    []
  );

  useEffect(() => {
    if (router.query.q && typeof router.query.q === 'string') {
      axios
        .get(
          `/api/search?query=${encodeURIComponent(router.query.q)}&page=${page}`
        )
        .then((res) => {
          setSearchResultList(res.data.data);
          setTotalPages(res.data.pageInfo.totalPages);
        });
    }
  }, [router.query.q, page]);

  return (
    <PageContainer>
      <main className="px-2">
        <IoChevronBack
          onClick={() => router.back()}
          className="text-xl text-y-gray mt-2"
        />
        <div className="flex justify-center m-4">
          <h1 className="font-bold text-xl lg:text-2xl">
            <span className="text-y-brown">
              &apos;
              {SearchMatcherToKor(router.query.q)}
              &apos;
            </span>{' '}
            검색 결과
          </h1>
        </div>
        <div className="m-4">
          {searchResultList.length === 0 ? (
            <div className="flex flex-col justify-center items-center rounded-lg bg-y-lightGray py-5">
              <Image
                className="m-auto pb-3 opacity-50"
                src="/images/logo.png"
                alt="logo"
                width={40}
                height={40}
              />
              <span>검색 결과가 없습니다</span>
            </div>
          ) : (
            searchResultList.map((el, idx) => {
              return (
                <Link key={el.beerId} href={`/beer/${el.beerId}`}>
                  <SearchCard cardProps={el} idx={idx} />
                </Link>
              );
            })
          )}
        </div>
        {searchResultList.length ? (
          <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
        ) : null}
      </main>
    </PageContainer>
  );
}
