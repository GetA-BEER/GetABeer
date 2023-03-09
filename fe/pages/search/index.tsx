import PageContainer from '@/components/PageContainer';
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
import BackBtn from '@/components/button/BackPageBtn';
import FollowUser, { FollowProps } from '@/components/followPage/FollowUser';
import { useRecoilState } from 'recoil';
import { accessToken } from '@/atoms/login';
export default function Search() {
  const router = useRouter();
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [searchResultList, setSearchResultList] = useState<SearchCardProps[]>(
    []
  );
  const [nameSearch, setNameSearch] = useState(false);
  const [userList, setUserList] = useState<FollowProps[]>([]);
  const searchQuery = router.query.q;
  const [TOKEN] = useRecoilState(accessToken);
  useEffect(() => {
    if (searchQuery && typeof searchQuery === 'string') {
      axios
        .get(
          `/api/search?query=${encodeURIComponent(searchQuery)}&page=${page}`
        )
        .then((res) => {
          if (searchQuery.includes('@') === true) {
            setUserList(res.data.data);
            setSearchResultList([]);
            setNameSearch(true);
          } else if (searchQuery.includes('@') === false) {
            setSearchResultList(res.data.data);
            setUserList([]);
            setNameSearch(false);
          }
          setTotalPages(res.data.pageInfo.totalPages);
        })
        .catch((err) => console.log(err));
    }
  }, [searchQuery, page, TOKEN]);

  return (
    <PageContainer>
      <main>
        <BackBtn />
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
          {nameSearch === false ? (
            <div>
              {searchResultList.length === 0 ? (
                <div className="noneContent py-8">
                  <Image
                    className="m-auto pb-3 opacity-50"
                    src="/images/logo.png"
                    alt="logo"
                    width={40}
                    height={40}
                  />
                  검색 결과가 없습니다.
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
          ) : (
            <div>
              {userList.length === 0 ? (
                <div className="noneContent">
                  <Image
                    className="m-auto pb-3 opacity-50"
                    src="/images/logo.png"
                    alt="logo"
                    width={40}
                    height={40}
                  />
                  검색된 유저가 없습니다
                </div>
              ) : (
                <div className="m-2 border divide-y divide-gray-200 rounded-xl">
                  {userList.map((el, idx) => (
                    <div key={idx}>
                      <FollowUser followprops={el} />
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}
        </div>

        {searchResultList.length || userList.length ? (
          <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
        ) : null}
      </main>
    </PageContainer>
  );
}
