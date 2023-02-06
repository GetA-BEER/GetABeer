import { BiUser } from 'react-icons/bi';

export default function ProfileCard(props: { nickname: string; date: string }) {
  return (
    <div className="px-4 pt-4 pb-2 flex items-center">
      <BiUser className="mr-1 p-[2px] bg-y-brown text-white rounded-full w-6 h-6" />
      <div>
        <div className="text-xs">{props.nickname}</div>
        <div className="text-y-gray text-[5px] -my-1 font-light">
          {props.date}
        </div>
      </div>
    </div>
  );
}
