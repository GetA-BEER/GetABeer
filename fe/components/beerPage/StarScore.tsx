import { GrStar } from 'react-icons/gr';
const StarScore = (score: any) => {
  console.log('score', score.score);
  const roundNum = Math.round(score.score);
  console.log('ceilNum', roundNum);
  return (
    <div className="flex">
      {[1, 2, 3, 4, 5].map((num) => (
        <span key={num}>
          {roundNum >= num ? (
            <GrStar className="text-amber-400 w-6 h-6" />
          ) : (
            <GrStar className="text-y-gray w-6 h-6" />
          )}
        </span>
      ))}
    </div>
  );
};

export default StarScore;
