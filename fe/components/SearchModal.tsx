import { useState, useEffect, useRef } from 'react';
import { BiSearch } from 'react-icons/bi';
import { MdCancel } from 'react-icons/md';
import SearchSwiper from './SearchSwiper';

type SearchProps = {
  setIsSearching: React.Dispatch<React.SetStateAction<boolean>>;
};

export default function SearchModal({ setIsSearching }: SearchProps) {
  const [inputState, setInputState] = useState('');
  const [searchHistoryList, setSearchHistoryList] = useState<string[] | []>([
    '검색어 저장은',
    '3개 정도만',
    '하는건 어떨까요',
  ]);
  const beerCategoryList = [
    '@에일',
    '@라거',
    '@밀맥주',
    '@흑맥주',
    '@필스너',
    '@과일',
    '@무알콜',
  ];

  const tagList = [
    '#짚색',
    '#금색',
    '#갈색',
    '#흑색',
    '#과일향',
    '#꽃향',
    '#맥아향',
    '#無향',
    '#단맛',
    '#신맛',
    '#쓴맛',
    '#떫은맛',
    '#탄산 약',
    '#탄산 중',
    '#탄산 강',
    '#탄산 無',
  ];

  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputState(e.target.value);
  };
  const inputRef = useRef<HTMLInputElement>(null);
  useEffect(() => {
    if (inputRef.current !== null) inputRef.current.focus();
  });
  return (
    <div
      className="fixed inset-0 w-full h-full z-50 bg-black/50"
      onClick={() => {
        setIsSearching(false);
      }}
    >
      <div
        className="flex flex-col max-w-4xl m-auto bg-white"
        onClick={(e) => e.stopPropagation()}
      >
        <form
          className="flex items-center h-12 rounded-xl p-2 bg-y-lightGray m-4"
          onSubmit={(e) => {
            e.preventDefault();
          }}
        >
          <BiSearch className="w-[30px] h-[30px] mr-1" />
          <input
            autoFocus
            ref={inputRef}
            type="text"
            placeholder="Search"
            className="w-full outline-none bg-y-lightGray"
            value={inputState}
            onChange={(e) => {
              onInputChange(e);
            }}
          ></input>
          {inputState !== '' ? (
            <button
              onClick={() => {
                setInputState('');
              }}
            >
              <MdCancel className="w-5 h-5 mx-1" />
            </button>
          ) : null}
        </form>
        <div className="mx-5 pb-2">
          <h4 className="text-y-brown">최근검색어</h4>
          <ul className="font-light">
            {searchHistoryList?.map((el, idx) => {
              return (
                <li
                  key={idx}
                  className="flex justify-between p-1 hover:bg-y-lightGray/80"
                >
                  {el}
                  <button className="text-y-gray" onClick={() => {}}>
                    <MdCancel className="mx-1 w-5 h-4" />
                  </button>
                </li>
              );
            })}
          </ul>
        </div>
        <div className="mx-5 pb-3">
          <h4 className="text-y-brown">카테고리 검색</h4>
          <SearchSwiper list={beerCategoryList} />
        </div>
        <div className="mx-5 pb-4">
          <h4 className="text-y-brown">태그 검색</h4>
          <SearchSwiper list={tagList} />
        </div>
      </div>
    </div>
  );
}
