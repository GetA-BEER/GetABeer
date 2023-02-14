import Image from 'next/image';
import Link from 'next/link';
import { useState, useRef, useEffect } from 'react';

import { BiSearch } from 'react-icons/bi';
export default function Header() {
  const [isSearching, setIsSearching] = useState(false);
  const [inputState, setInputState] = useState('');
  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputState(e.target.value);
  };
  const inputRef = useRef<HTMLInputElement>(null);
  useEffect(() => {
    if (inputRef.current !== null) inputRef.current.focus();
  });
  return (
    <div className="flex justify-between items-center max-w-4xl m-auto h-16 border-b border-y-lightGray bg-white sticky top-0 z-10">
      <Link href={'/'}>
        <Image
          alt="logo"
          width={50}
          height={50}
          src="/images/logo.png"
          className="ml-4"
        />
      </Link>
      <div
        className={
          isSearching
            ? 'flex items-center w-full rounded-xl p-2 bg-y-lightGray mx-4'
            : 'hidden'
        }
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
          onBlur={() => {
            if (inputState === '') {
              setIsSearching(false);
            }
          }}
        ></input>
      </div>
      <button
        className={isSearching ? 'hidden' : 'mr-4'}
        onClick={() => {
          setIsSearching(true);
        }}
      >
        <BiSearch className="w-[30px] h-[30px]" />
      </button>
    </div>
  );
}
