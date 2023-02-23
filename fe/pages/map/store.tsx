import PageContainer from '@/components/PageContainer';
import Map from '@/components/middleCards/Map';

export default function StoreMap() {
  return (
    <PageContainer>
      <div className="w-full h-full">
        <div className="flex justify-center my-2">
          <h1 className="text-2xl sm:text-3xl lg:text-4xl font-bold">편의점</h1>
        </div>
        <div className="w-full h-full">
          <Map latitude={37.553836} longitude={126.969652} />
        </div>
      </div>
    </PageContainer>
  );
}
