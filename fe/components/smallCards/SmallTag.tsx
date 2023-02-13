export default function SmallTag(props: { tags: string[] }) {
  return (
    <div className="flex flex-wrap h-fit mb-1">
      {props.tags.map((el, idx) => (
        <div
          key={idx}
          className="px-[2px] py-[1px] my-[1px] rounded-[4px]  bg-y-gold text-white text-[10px] mr-0.5 "
        >
          #{el}
        </div>
      ))}
    </div>
  );
}
