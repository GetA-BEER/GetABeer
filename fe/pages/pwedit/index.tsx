import SubmitBtn from '@/components/button/SubmitBtn';
import { Input } from '@/components/inputs/Input';
import Head from 'next/head';
import { IoClose } from 'react-icons/io5';
import { useForm } from 'react-hook-form';
import { BiErrorAlt } from 'react-icons/bi';
import axios from '@/pages/api/axios';
import Router from 'next/router';
import Link from 'next/link';
import swal from 'sweetalert2';

interface IFormValues {
  email: string;
  password: string;
  name: string;
  text: string;
  passwordConfirm: string;
  editpassword: string;
}
export default function PwEdit() {
  const {
    register,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm<IFormValues>({ mode: 'onChange' });
  const onValid = (data: any) => {
    // 기본으로 data 가져오기
    // console.log(data);
    const { password, editpassword, passwordConfirm } = getValues();
    pwEditClick(password, editpassword, passwordConfirm);
  };
  const pwEditClick = (
    password: string,
    editpassword: string,
    passwordConfirm: string
  ) => {
    const reqBody = {
      oldPassword: password,
      newPassword: editpassword,
      newVerifyPassword: passwordConfirm,
    };
    axios
      .patch('api/user/password', reqBody)
      .then((res) => {
        // console.log(res);
        swal.fire({
          text: '비밀번호를 변경하였습니다',
          confirmButtonColor: '#F1B31C',
          confirmButtonText: '확인',
        });
        Router.back();
      })
      .catch((err) => {
        console.log(err);
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
        <Link href={'/myedit'}>
          <button className="m-4">
            <IoClose className="w-6 h-6" />
          </button>
        </Link>
        <div className="my-4 text-center text-lg bg-white rounded-lg font-semibold">
          비밀번호 변경
        </div>
        <div className="m-auto max-w-md">
          <form onSubmit={handleSubmit(onValid)}>
            <Input
              name="password"
              type="password"
              placeholder="현재 비밀번호 입력"
              register={register}
              rules={{
                required: '필수 입력 항목입니다.',
                pattern: {
                  value:
                    /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,16}$/,
                  message:
                    '비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.',
                },
              }}
            />
            {errors.password && (
              <p className="flex text-xs mx-3 gap-0.5 text-red-500 font-light">
                <BiErrorAlt />
                {errors.password.message}
              </p>
            )}
            <Input
              name="editpassword"
              type="password"
              placeholder="변경할 비밀번호 입력"
              register={register}
              rules={{
                required: '필수 입력 항목입니다.',
                pattern: {
                  value:
                    /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,16}$/,
                  message:
                    '비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.',
                },
              }}
            />
            {errors.editpassword && (
              <p className="flex text-xs mx-3 gap-0.5 text-red-500 font-light">
                <BiErrorAlt />
                {errors.editpassword.message}
              </p>
            )}
            <Input
              name="passwordConfirm"
              type="password"
              placeholder="변경할 비밀번호 확인"
              register={register}
              rules={{
                required: '확인을 위해 비밀번호를 한 번 더 입력해주세요.',
                validate: {
                  matchesPreviousPassword: (value) => {
                    const { editpassword } = getValues();
                    return (
                      editpassword === value || '비밀번호가 일치하지 않습니다!'
                    );
                  },
                },
              }}
            />
            {errors.passwordConfirm && (
              <p className="flex text-xs mx-3 gap-0.5 text-red-500 font-light">
                <BiErrorAlt />
                {errors.passwordConfirm.message}
              </p>
            )}
            <SubmitBtn onClick={undefined}>비밀번호 변경</SubmitBtn>
          </form>
        </div>
      </main>
    </>
  );
}
