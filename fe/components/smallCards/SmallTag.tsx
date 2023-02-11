export default function SmallTag(props: { tags: string[] }) {
  return (
    <div>
      {props.tags.map((el, idx) => (
        <span
          key={idx}
          className="px-1 py-[5px] rounded-md my-3 bg-y-gold text-white text-[8px] sm:text-xs lg:text-sm mr-0.5 font-light"
        >
          #{el}
        </span>
      ))}
    </div>
  );
}
