import Image from 'next/image';
import Link from 'next/link';
import { useState, useEffect } from 'react';

import { BiSearch } from 'react-icons/bi';
import SearchModal from './SearchModal';

import { useRecoilValue } from 'recoil';
import { accessToken } from '@/atoms/login';
import axios from '@/pages/api/axios';
import Alarm from './Alarm';

export default function Header() {
  const [isSearching, setIsSearching] = useState(false);
  const TOKEN = useRecoilValue(accessToken);

  useEffect(() => {
    if (TOKEN) {
      const config = {
        headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
        withCredentials: true,
      };
      axios
        .post('/api/refresh', {}, config)
        .then((res) => {
          axios.defaults.headers.common['Authorization'] =
            res.headers.authorization;
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [TOKEN]);
  return (
    <div className="w-full bg-white sticky top-0 z-10 border-b mb-6">
      <div className="flex justify-between items-center max-w-4xl m-auto h-16 border-y-lightGray ">
        <Link href={'/'}>
          <Image
            alt="logo"
            width={48}
            height={48}
            src="/images/logo.png"
            className="ml-4 mr-2"
          />
        </Link>
        <div className="flex justify-end items-center">
          {isSearching ? <SearchModal setIsSearching={setIsSearching} /> : null}
          <button
            onClick={() => {
              setIsSearching(true);
            }}
          >
            <BiSearch className="w-[30px] h-[30px]" />
          </button>
          <Alarm />
        </div>
      </div>
    </div>
  );
}
