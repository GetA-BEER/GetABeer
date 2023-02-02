import { useState } from 'react';
export default function SelectTag() {
  const colorList = ['짚색', '금색', '갈색', '흑색'];
  const tasteList = ['단맛', '신맛', '쓴맛', '떫은맛'];
  const smellList = ['과일향', '꽃향', '맥아향', '無향'];
  const sparkleList = ['탄산 약', '탄산 중', '탄산 강', '탄산 無'];
  const [checked, setChecked] = useState(false);
  const onCategoryChange = (checked: string) => {
    setChecked(true);
    console.log(checked);
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
              className="peer hidden"
              onClick={() => {
                onCategoryChange(el);
              }}
              checked
              readOnly
            />
            <label
              htmlFor={el}
              className="text-xs block cursor-pointer select-none rounded-xl p-2 text-center peer-checked: border-2 peer-checked:border-y-gold"
            >
              {el}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
}
