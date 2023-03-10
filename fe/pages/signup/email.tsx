import SubmitBtn from '@/components/button/SubmitBtn';
import Router from 'next/router';
import Link from 'next/link';
import { BiErrorAlt } from 'react-icons/bi';
import { Input } from '@/components/inputs/Input';
import Head from 'next/head';
import { IoChevronBack } from 'react-icons/io5';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import Timer from '@/components/signup/Timer';
import axios from '@/pages/api/axios';
import swal from 'sweetalert2';
interface IFormValues {
  email: string;
  password: string;
  name: string;
  text: string;
  passwordConfirm: string;
  editpassword: string;
}

export default function Email() {
  const {
    register,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm<IFormValues>({ mode: 'onChange' });

  const [showModal, setShowModal] = useState(false);
  const [timeMessage, setTimeMessage] = useState('');
  const [emailMessage, setEmailMessage] = useState('');
  const onValid = (data: any) => {
    // 기본으로 data 가져오기
    // console.log(data);
    const { email } = getValues();
    emailClick(email);
  };
  const onClickCheck = (data: any) => {
    // console.log(data);
    const { email, text } = getValues();
    handleClickCheck(email, text);
  };
  const emailClick = (email: string) => {
    const reqBody = {
      email: email,
    };
    axios
      .post('/api/mail', reqBody)
      .then((res) => {
        // console.log(res);
        swal.fire({
          text: '인증코드가 전송되었습니다.',
          confirmButtonColor: '#F1B31C',
          confirmButtonText: '확인',
        });
        setEmailMessage('');
        setShowModal(true);
      })
      .catch((err) => {
        console.log(err);
        if (err.response.data.status === 409) {
          setEmailMessage(err.response.data.message);
        }
      });
  };
  const handleClickCheck = (email: string, text: string) => {
    const reqBody = {
      email: email,
      code: text,
    };
    axios
      .post('/api/mail/check', reqBody)
      .then((res) => {
        Router.push({
          pathname: '/signup',
          query: { email: res.data },
        });
        setTimeMessage('');
      })
      .catch((err) => {
        // console.log(err);
        setTimeMessage('올바른 인증코드가 아닙니다.');
      });
  };
  return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>
      <main className="m-auto h-screen max-w-4xl">
        <Link href={'/login'}>
          <button className="m-4">
            <IoChevronBack className="w-6 h-6" />
          </button>
        </Link>
        <div className="my-4 text-center text-lg bg-white rounded-lg font-semibold">
          회원가입
        </div>
        <div className="m-auto max-w-md">
          <form onSubmit={handleSubmit(onValid)}>
            <Input
              name="email"
              type="email"
              placeholder="email@gmail.com"
              register={register}
              rules={{
                required: true,
              }}
            />
            {emailMessage ? (
              <div className="flex px-3 gap-0.5 text-red-600 text-xs">
                <BiErrorAlt />
                {emailMessage}
              </div>
            ) : null}
            <SubmitBtn onClick={undefined}> 이메일 인증 </SubmitBtn>
          </form>
          {showModal ? (
            <div className="m-2 pb-2 pt-4 px-2 bg-gray-100 rounded-xl relative">
              <label className="flex justify-center text-xs">
                이메일로 전송된 인증코드를 입력해주세요.
              </label>
              <div className="max-w-md right-3 absolute p-4">
                <Timer active />
              </div>
              <Input
                name="text"
                type="text"
                placeholder="인증번호를 입력해주세요."
                register={register}
              />
              {timeMessage ? (
                <div className="flex justify-center gap-0.5 text-red-600 text-xs">
                  <BiErrorAlt />
                  올바른 인증코드가 아닙니다.
                </div>
              ) : null}

              <SubmitBtn onClick={onClickCheck}> 확인 </SubmitBtn>
            </div>
          ) : null}
          <div className="my-3 flex justify-center gap-1.5 text-sm">
            <div className="text-y-gray font-light">이미 계정이 있다면?</div>
            <Link href={'/login'}>
              <button className="flex text-y-brown">로그인</button>
            </Link>
          </div>
        </div>
      </main>
    </>
  );
}
