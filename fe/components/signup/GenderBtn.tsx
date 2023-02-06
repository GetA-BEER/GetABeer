const list = ['남', '여'];

export default function GenderBtn() {
  return (
    <div className="w-24 h-11 m-2 p-1 grid grid-cols-2 gap-1 mx-2 items-center rounded-full border border-y-gray">
      {list.map((el: string, idx: number) => (
        <div key={idx.toString()}>
          <input
            type="radio"
            name="gender"
            id={el}
            value={el}
            className="peer hidden"
            checked
            readOnly
          />
          <label
            htmlFor={el}
            className="text-xs block cursor-pointer select-none rounded-full p-2 text-center peer-checked:bg-y-gold"
          >
            {el}
          </label>
        </div>
      ))}
    </div>
  );
}
