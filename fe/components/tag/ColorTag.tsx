type tagProps = {
  setSelected: React.Dispatch<React.SetStateAction<string>>;
  checked: string | undefined;
};

export default function ColorTag({ setSelected, checked }: tagProps) {
  const colorList = ['짚색', '금색', '갈색', '흑색'];
  const onClick = (e: React.MouseEvent<HTMLInputElement>) => {
    setSelected((e.target as HTMLInputElement).value);
  };
  return (
    <div className="my-4">
      <div className="grid grid-cols-4 mx-2 gap-2 items-center">
        {colorList.map((el: string, idx: number) => (
          <div key={idx.toString()}>
            <input
              type="radio"
              name="color"
              id={el}
              value={el}
              defaultChecked={checked === el ? true : false}
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
