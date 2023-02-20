export default function PageContainer({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="m-auto h-screen max-w-4xl">
      {children}
      <div className="pb-14"></div>
    </div>
  );
}
