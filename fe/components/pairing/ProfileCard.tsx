import Image from 'next/image';

export default function ProfileCard(props: {
  nickname: string;
  date: string;
  userImage: string;
}) {
  return (
    <div className="px-4 pt-4 pb-2 flex items-center">
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
