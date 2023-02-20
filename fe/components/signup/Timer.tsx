import React, { useState, useEffect } from 'react';
type TimerProps = {
  active: boolean;
};
const Timer = ({ active }: TimerProps) => {
  const [min, setMin] = useState(5);
  const [sec, setSec] = useState(0);

  useEffect(() => {
    if (active) {
      const timer = setInterval(() => {
        if (Number(sec) > 0) {
          setSec(Number(sec) - 1);
        }
        if (Number(sec) === 0) {
          if (Number(min) === 0) {
            clearInterval(timer);
          } else {
            setMin(Number(min) - 1);
            setSec(59);
          }
        }
      }, 1000);
      return () => clearInterval(timer);
    }
  }, [min, sec, active]);

  return (
    <span className="text-red-600 font-light">
      {min}:{sec < 10 ? `0${sec}` : sec}
    </span>
  );
};

export default Timer;
