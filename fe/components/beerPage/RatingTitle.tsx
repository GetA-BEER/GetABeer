export interface ratingCount {
  count?: number;
}

export default function RatingTitle(props: { ratingCount: number }) {
  return (
    <div className="max-w-4xl flex justify-between mx-5 mb-4">
      <div className="flex items-center">
        <span className="font-semibold mr-1 text-sm">코멘트</span>
        <span className="text-xs">{props.ratingCount}</span>
      </div>
      <span className="text-xs">모두보기</span>
    </div>
  );
}
