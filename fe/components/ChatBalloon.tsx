export function ChatBalloonLeft({ children }: { children: React.ReactNode }) {
  return (
    <div
      className="w-fit max-w-[220px] md:max-w-[320px] h-fit relative ml-4 p-1 rounded-r-lg rounded-b-lg bg-y-brown text-xs font-thin text-white
      after:border-t-[12px] after:border-l-[16px] after:border-t-y-brown after:border-l-transparent after:absolute after:top-0 after:-left-4"
    >
      {children}
    </div>
  );
}

export function ChatBalloonRight({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex justify-end">
      <div
        className="w-fit max-w-[220px] md:max-w-[320px] h-fit relative mr-4 p-1 rounded-l-lg rounded-b-lg bg-y-cream text-xs font-thin
      after:border-t-[12px] after:border-r-[17px] after:border-t-y-cream after:border-r-transparent after:absolute after:top-0 after:-right-4"
      >
        {children}
      </div>
    </div>
  );
}
