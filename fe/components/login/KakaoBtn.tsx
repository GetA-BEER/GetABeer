import { RiKakaoTalkFill } from 'react-icons/ri';
type ButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  //onClick이벤트 타입
};
export default function KakaoBtn({ onClick }: ButtonProps) {
  return (
    <div>
      <button
        onClick={onClick}
        className="flex justify-center items-center w-11 h-11 rounded-full bg-yellow-400 hover:bg-yellow-200 text-xs"
      >
        <RiKakaoTalkFill className="w-6 h-6" />
      </button>
    </div>
  );
}
