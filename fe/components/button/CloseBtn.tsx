type ButtonProps = {
  children: string;
  onClick: React.MouseEventHandler<HTMLButtonElement> | undefined;
  //onClick이벤트 타입
};
export default function CloseBtn({ children, onClick }: ButtonProps) {
  return (
    <div className="p-2">
      <button
        onClick={onClick}
        className="flex justify-center items-center w-full h-11 rounded-xl bg-y-cream hover:bg-orange-200 text-xs"
      >
        {children}
      </button>
    </div>
  );
}
