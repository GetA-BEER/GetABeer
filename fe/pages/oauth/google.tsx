import { useRouter } from 'next/router';
import { useEffect } from 'react';
import axios from '@/pages/api/axios';
import { useRecoilState } from 'recoil';
import { accessToken, userId } from '@/atoms/login';
import swal from 'sweetalert2';
export default function Naver() {
  const [TOKEN, setAccessToken] = useRecoilState(accessToken);
  const [ID, setUserId] = useRecoilState(userId);
  const router = useRouter();
  const code = router.query.code;
  useEffect(() => {
    if (code !== undefined) {
      axios
        .get(`/oauth/google?code=${code}`)
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
    if (TOKEN) {
      axios
        .get('/api/user')
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
  return <main className="px-2"></main>;
}
