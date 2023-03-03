import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import axios from '@/pages/api/axios';
export default function Kakao() {
  const router = useRouter();
  console.log(router.query);
  const code = router.query.code;
  useEffect(() => {
    axios
      .get(`/oauth/kakao?code=${code}`)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => console.log(error));
  }, [code]);
  return <main className="px-2"></main>;
}
