import { FaThumbsUp, FaRegThumbsUp } from 'react-icons/fa';
import axios from '@/pages/api/axios';
import { useState } from 'react';

// "isUserLikes": true,  "likeCount": 1,
export default function PairingThumbs({
  isUserLikes,
  likeCount,
  pairingId,
}: any) {
  const [likesState, setLikesState] = useState<boolean | undefined>(
    isUserLikes
  );
  const handleClick = () => {
    if (likesState !== undefined) {
      const config = {
        headers: {
          authorization:
            'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJVU0VSIl0sImVtYWlsIjoibWgwNTAyMzFAbmF2ZXIuY29tIiwic3ViIjoibWgwNTAyMzFAbmF2ZXIuY29tIiwiaWF0IjoxNjc2OTYxNTMwLCJleHAiOjE2NzY5Njg3MzB9.SwHIDzC-DBoJmcCy8mEiqd1Hyt19IQuy5yFyHqE3UgCw82vOz7eHL_WzgE2npjho1cUz8SVmNUziJ2Xn1Tt_2Q',
        },
        withCredentials: true,
      };

      // axios
      //   .post(`/api/pairings/likes?pairingId=${pairingId}`, {}, config)
      //   .then((response) => {
      //     console.log(response);
      setLikesState(!likesState);
      // })
      // .catch((error) => console.log(error));
    }
  };
  return (
    <span onClick={handleClick} className="z-10 ">
      {likesState ? (
        <span className="mx-1 flex justify-center">
          <FaThumbsUp className="w-3 h-3" />
          {likeCount}
        </span>
      ) : (
        <span className="mx-1 flex justify-center">
          <FaRegThumbsUp className="w-3 h-3" /> {likeCount}
        </span>
      )}
    </span>
  );
}
