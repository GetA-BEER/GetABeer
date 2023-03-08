import { useRouter } from 'next/router';
import { IoChevronBack } from 'react-icons/io5';

export default function BackBtn() {
  const router = useRouter();
  return (
    <button
      type="button"
      onClick={() => {
        router.back();
      }}
      className="ml-4 absolute"
    >
      <IoChevronBack className="w-6 h-6" />
    </button>
  );
}
