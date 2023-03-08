import PageContainer from '@/components/PageContainer';
import { useRecoilState } from 'recoil';
import { searchingImage } from '@/atoms/searchingImage';
import Link from 'next/link';
import Image from 'next/image';
import BackBtn from '@/components/button/BackPageBtn';
import SearchCard from '@/components/middleCards/SearchCard';

export default function ImageSearch() {
  const [searchResultList] = useRecoilState(searchingImage);
  return (
    <PageContainer>
      <main className="px-2">
        <BackBtn />
        <div className="flex justify-center m-4">
          <h1 className="font-bold text-xl lg:text-2xl">이미지 검색 결과</h1>
        </div>
        <div className="m-4">
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
      </main>
    </PageContainer>
  );
}
