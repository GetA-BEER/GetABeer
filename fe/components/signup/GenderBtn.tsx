const list = ['남', '여', '공개 안함'];

export default function GenderBtn() {
  return (
    <div className="flex justify-between">
      <div className="m-2 self-center text-sm">성별</div>
      <div className="h-10 w-52 m-2 p-1 grid grid-cols-3 mx-2 items-center rounded-xl border border-y-gray">
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
              className="text-xs block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-y-gold"
            >
              {el}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
}
