import Image from 'next/image';

export default function ProfileCard(props: {
  nickname: string;
  date: string;
  src: string;
}) {
  return (
    <div className="px-4 pt-4 pb-2 flex items-center">
      {props.src === undefined ? (
        <></>
      ) : (
        <Image
          alt="user profile image"
          src={props.src}
          fill
          className="object-cover"
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
