export default function InterestTag() {
  const interestList = [
    '짚색',
    '금색',
    '갈색',
    '흑색',
    '과일향',
    '꽃향',
    '맥아향',
    '無향',
    '탄산 약',
    '탄산 중',
    '탄산 강',
    '탄산 無',
    '단맛',
    '신맛',
    '쓴맛',
    '떫은맛',
  ];

  return (
    <div className="px-2 pt-2">
      <div className="text-sm">관심 태그 (최대 4개까지 선택 가능)</div>
      <div className="grid grid-cols-4 my-2 gap-2 w-full items-center">
        {interestList.map((el: string, idx: number) => (
          <div key={idx.toString()}>
            <input
              type="checkbox"
              name="interest"
              id={el}
              value={el}
              className="peer hidden"
            />
            <label
              htmlFor={el}
              className="text-xs h-10 w-full inline-flex items-center justify-center cursor-pointer select-none rounded-xl p-1 text-center peer-checked: border-2 peer-checked:border-y-brown"
            >
              {el}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
}
