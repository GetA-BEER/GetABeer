import PageContainer from '@/components/PageContainer';
import { useRecoilState } from 'recoil';
import { searchingImage } from '@/atoms/searchingImage';
import Link from 'next/link';
import Loading from '@/components/postPairingPage/Loading';
import BackBtn from '@/components/button/BackPageBtn';
import SearchCard from '@/components/middleCards/SearchCard';

export default function ImageSearch() {
  const [searchResultList, setSearchResultList] =
    useRecoilState(searchingImage);

  return (
    <PageContainer>
      <main className="px-2">
        <BackBtn />
        <div className="flex justify-center m-4">
          <h1 className="font-bold text-xl lg:text-2xl">이미지 검색 결과</h1>
        </div>
        <div className="m-4">
          {searchResultList.length === 0 ? (
            <div className="inset-0 flex justify-center items-center fixed z-10 ">
              <div className="w-fit m-2 p-5 z-[11] text-base lg:text-lg text-y-gold rounded-lg">
                <Loading />
              </div>
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
