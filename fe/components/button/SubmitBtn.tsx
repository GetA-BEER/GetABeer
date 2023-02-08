type ButtonProps = {
  children: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  //onClick이벤트 타입
};
export default function SubmitBtn({ children, onClick }: ButtonProps) {
  return (
    <div className="p-2">
      <button
        onClick={onClick}
        className="flex justify-center items-center w-full h-11 rounded-xl bg-y-gold hover:bg-orange-400 text-xs"
      >
        {children}
      </button>
    </div>
  );
}
