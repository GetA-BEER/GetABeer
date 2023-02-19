export default function Tag({ children }: { children: React.ReactNode }) {
  return (
    <span className="justify-center items-center px-2 py-[5px] rounded-md mt-3 bg-y-gold text-white text-[8px] sm:text-xs lg:text-sm mr-0.5">
      #{children}
    </span>
  );
}
