/* eslint-disable react-hooks/exhaustive-deps */
import { useRouter } from 'next/router';
import { useEffect } from 'react';
import axios from '@/pages/api/axios';
import { useRecoilState } from 'recoil';
import { accessToken, userId } from '@/atoms/login';
import swal from 'sweetalert2';
import Loading from '@/components/postPairingPage/Loading';
export default function Naver() {
  const [TOKEN, setAccessToken] = useRecoilState(accessToken);
  const [ID, setUserId] = useRecoilState(userId);
  const router = useRouter();
  const code = router.query.code;
  useEffect(() => {
    const config = {
      headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
      withCredentials: true,
    };
    if (code !== undefined) {
      axios
        .get(`/oauth/google?code=${code}`, config)
        .then((res) => {
          setAccessToken(res.headers.authorization);
          setUserId(res.data.id);
          axios.defaults.headers.common['Authorization'] =
            res.headers.authorization;
        })
        .catch((error) => console.log(error));
    }
  }, [code]);
  useEffect(() => {
    const config = {
      headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
      withCredentials: true,
    };
    if (TOKEN) {
      axios
        .get('/api/user', config)
        .then((res) => {
          // console.log(res);
          if (res.data.age !== null) {
            swal.fire({
              title: 'Get A Beer',
              text: '로그인이 완료되었습니다.',
              confirmButtonColor: '#F1B31C',
              confirmButtonText: '확인',
            });
            router.push({
              pathname: '/',
            });
          } else {
            router.push({
              pathname: '/signup/oauthInformation',
              query: { user_id: ID },
            });
          }
        })
        .catch((err) => console.log(err));
    }
  }, [TOKEN]);
  return (
    <main className="px-2">
      <div className="inset-0 flex justify-center items-center fixed z-10 bg-[rgb(0,0,0,0.3)]">
        <div className="w-fit m-2 p-5 z-[11] bg-white text-base lg:text-lg text-y-gold rounded-lg">
          <Loading />
          <div className="mt-5 text-center text-y-brown text-sm">
            <div>로그인중입니다.</div>
            <div>잠시만 기다려 주세요</div>
          </div>
        </div>
      </div>
    </main>
  );
}
