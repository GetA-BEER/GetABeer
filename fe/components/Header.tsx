import Image from 'next/image';
import Link from 'next/link';
import { useState, useEffect } from 'react';

import { BiSearch } from 'react-icons/bi';
import SearchModal from './SearchModal';

// import { useRecoilValue } from 'recoil';
// import { accessToken } from '@/atoms/login';
// import axios from '@/pages/api/axios';
export default function Header() {
  const [isSearching, setIsSearching] = useState(false);
  // const TOKEN = useRecoilValue(accessToken);

  // useEffect(() => {
  //   if (TOKEN) {
  //     const headers = { Authorization: TOKEN };
  //     axios
  //       .post('/api/refresh', {
  //         headers: headers,
  //       })
  //       .then((res) => {
  //         axios.defaults.headers.common['Authorization'] =
  //           res.headers.authorization;
  //       })
  //       .catch((err) => {
  //         console.log(err);
  //       });
  //   }
  // }, [TOKEN]);
  return (
    <div className="flex justify-between items-center max-w-4xl m-auto h-16 border-b border-y-lightGray bg-white sticky top-0 z-10">
      <Link href={'/'}>
        <Image
          alt="logo"
          width={50}
          height={50}
          src="/images/logo.png"
          className="ml-4 mr-2"
        />
      </Link>
      {isSearching ? <SearchModal setIsSearching={setIsSearching} /> : null}
      <button
        className="mr-4"
        onClick={() => {
          setIsSearching(true);
        }}
      >
        <BiSearch className="w-[30px] h-[30px]" />
      </button>
    </div>
  );
}
