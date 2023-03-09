import Image from 'next/image';
import { useRecoilValue } from 'recoil';
import { userId } from '@/atoms/login';
import { useRouter } from 'next/router';
export default function ProfileCard(props: {
  nickname: string;
  date: string;
  userImage: string;
  userId: number;
}) {
  const router = useRouter();
  const USERID = useRecoilValue(userId);
  const userCheck = () => {
    if (USERID !== props?.userId) {
      router.push(`/userpage/${props?.userId}`);
    } else {
      router.push(`/mypage`);
    }
  };
  return (
    <div className="px-4 pt-4 pb-2 flex items-center" onClick={userCheck}>
      {props.userImage === undefined ? (
        <></>
      ) : (
        <Image
          alt="userImg"
          src={props?.userImage}
          width={100}
          height={100}
          className="w-7 h-7 mr-1 rounded-full"
          priority
        />
      )}
      <div>
        <div className="text-xs">{props.nickname}</div>
        <div className="text-y-gray text-[5px] -my-1 font-light">
          {props.date}
        </div>
      </div>
    </div>
  );
}
