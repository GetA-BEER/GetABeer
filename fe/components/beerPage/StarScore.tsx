import { TiStarHalf, TiStar } from 'react-icons/ti';
const StarScore = (score: any) => {
  // 소수점 첫째자리 반올림
  const roundNum = Math.round(score.score * 100) / 100;

  return (
    <div className="flex">
      {[1, 2, 3, 4, 5].map((num) => (
        <span key={num}>
          {roundNum >= num ? (
            <TiStar className="text-amber-300 w-6 h-6" />
          ) : num > roundNum && roundNum >= num - 0.5 ? (
            <span className="relative">
              <TiStar className="text-y-gray w-6 h-6 " />
              <TiStarHalf className="text-amber-300 w-6 h-6 absolute top-0" />
            </span>
          ) : (
            <TiStar className="text-y-gray w-6 h-6" />
          )}
        </span>
      ))}
    </div>
  );
};

export default StarScore;
