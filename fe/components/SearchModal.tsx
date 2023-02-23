import { useState, useEffect, useRef } from 'react';
import { BiSearch } from 'react-icons/bi';
import { MdCancel } from 'react-icons/md';
import SearchSwiper from './SearchSwiper';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { searchHistory } from '@/atoms/searchHistory';
type SearchProps = {
  setIsSearching: React.Dispatch<React.SetStateAction<boolean>>;
};

interface searchWord {
  id: number;
  word: string;
}

export default function SearchModal({ setIsSearching }: SearchProps) {
  const router = useRouter();
  const [inputState, setInputState] = useState<string>('');
  const [searchHistoryList, setSearchHistoryList] =
    useRecoilState(searchHistory);
  useEffect(() => {
    if (typeof window !== 'undefined') {
      const history = localStorage.getItem('searchHistoryList') || '[]';
      setSearchHistoryList(JSON.parse(history));
    }
  }, []);

  useEffect(() => {
    localStorage.setItem(
      'searchHistoryList',
      JSON.stringify(searchHistoryList)
    );
  }, [searchHistoryList]);

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

  const onSearch = () => {
    router.push({ pathname: '/search', query: { q: inputState } });
    addSearchHistory(inputState);
    setIsSearching(false);
  };

  const addSearchHistory = (word: string) => {
    const newSearchWord = {
      id: Date.now(),
      word,
    };
    setSearchHistoryList([newSearchWord, ...searchHistoryList]);
  };

  const removeSearchHistory = (id: number) => {
    const filtered = searchHistoryList.filter((el: searchWord) => {
      return el.id !== id;
    });
    setSearchHistoryList(filtered);
  };

  const clearSearchHistory = () => {
    setSearchHistoryList([]);
  };

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
            onKeyUp={(e) => {
              if (e.key === 'Enter') {
                onSearch();
              }
            }}
          ></input>
          {inputState !== '' ? (
            <button
              type="button"
              onClick={() => {
                setInputState('');
              }}
            >
              <MdCancel className="w-5 h-5 mx-1" />
            </button>
          ) : null}
        </form>
        <div className="mx-5 pb-2">
          <div className="flex justify-between">
            <h4 className="text-y-brown">최근검색어</h4>
            {searchHistoryList.length ? (
              <button
                className="text-y-gray/80 font-light text-sm"
                onClick={clearSearchHistory}
              >
                전체 삭제
              </button>
            ) : null}
          </div>
          <ul className="font-light">
            {searchHistoryList.length
              ? searchHistoryList?.map((el: searchWord) => {
                  return (
                    <li
                      key={el.id}
                      className="flex justify-between p-1 hover:bg-y-lightGray/80"
                    >
                      {el.word}
                      <button
                        className="text-y-gray"
                        onClick={() => removeSearchHistory(el.id)}
                      >
                        <MdCancel className="mx-1 w-5 h-4" />
                      </button>
                    </li>
                  );
                })
              : null}
          </ul>
        </div>
        <div className="mx-5 pb-3">
          <h4 className="text-y-brown">카테고리 검색</h4>
          <SearchSwiper
            list={beerCategoryList}
            setIsSearching={setIsSearching}
          />
        </div>
        <div className="mx-5 pb-4">
          <h4 className="text-y-brown">태그 검색</h4>
          <SearchSwiper list={tagList} setIsSearching={setIsSearching} />
        </div>
      </div>
    </div>
  );
}
