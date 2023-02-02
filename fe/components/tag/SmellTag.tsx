export default function SmellTag() {
  const smellList = ['과일향', '꽃향', '맥아향', '無향'];

  return (
    <div className="my-4">
      <div className="grid grid-cols-4 mx-2 gap-2 items-center">
        {smellList.map((el: string, idx: number) => (
          <div key={idx.toString()}>
            <input
              type="radio"
              name="smell"
              id={el}
              value={el}
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
