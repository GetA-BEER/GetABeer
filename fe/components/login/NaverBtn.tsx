type ButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  //onClick이벤트 타입
};
export default function NaverBtn({ onClick }: ButtonProps) {
  return (
    <div>
      <button
        onClick={onClick}
        className="flex justify-center items-center w-11 h-11 rounded-full bg-green-500 hover:bg-green-700 text-xs"
      >
        <div className="w-6 h-6 text-white text-center text-xl font-black leading-6">
          N
        </div>
      </button>
    </div>
  );
}
