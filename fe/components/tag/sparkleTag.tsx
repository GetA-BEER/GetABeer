type tagProps = {
  setSelected: React.Dispatch<React.SetStateAction<string>>;
};

export default function SparkleTag({ setSelected }: tagProps) {
  const sparkleList = ['탄산 약', '탄산 중', '탄산 강', '탄산 無'];
  const onClick = (e: React.MouseEvent<HTMLInputElement>) => {
    setSelected((e.target as HTMLInputElement).value);
  };
  return (
    <div className="my-4">
      <div className="grid grid-cols-4 mx-2 gap-2 items-center">
        {sparkleList.map((el: string, idx: number) => (
          <div key={idx.toString()}>
            <input
              type="radio"
              name="sparkl"
              id={el}
              value={el}
              onClick={onClick}
              className="peer hidden"
            />
            <label
              htmlFor={el}
              className="text-xs block cursor-pointer select-none rounded-xl p-2 text-center peer-checked: border-2 peer-checked:border-y-brown"
            >
              {el}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
}
