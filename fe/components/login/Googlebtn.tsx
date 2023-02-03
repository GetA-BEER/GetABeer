import { FcGoogle } from 'react-icons/fc';
type ButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  //onClick이벤트 타입
};
export default function GoogleBtn({ onClick }: ButtonProps) {
  return (
    <div>
      <button
        onClick={onClick}
        className="flex justify-center items-center w-11 h-11 rounded-full border border-y-gray hover:bg-gray-200 text-xs"
      >
        <FcGoogle className="w-6 h-6" />
      </button>
    </div>
  );
}
