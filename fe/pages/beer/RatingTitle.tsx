export interface ratingCount {
  count?: number;
}

export default function RatingTitle(ratingCount: number | undefined) {
  return (
    <div className="max-w-4xl">
      <div>
        코멘트<span>{ratingCount}</span>
      </div>
      <div>모두보기</div>
    </div>
  );
}
