import PageContainer from '@/components/PageContainer';
import Map from '@/components/middleCards/Map';
import { useEffect, useState } from 'react';

export default function StoreMap() {
  const [latitude, setLatitude] = useState<number>(0);
  const [longitude, setLongitude] = useState<number>(0);
  useEffect(() => {
    const options = {
      enableHighAccuracy: true,
      timeout: 1000,
      maximumAge: 0,
    };
    function success(pos: any) {
      const crd = pos.coords;
      setLatitude(crd.latitude);
      setLongitude(crd.longitude);
    }

    function error(err: any) {
      console.warn(`ERROR(${err.code}): ${err.message}`);
    }
    navigator.geolocation.getCurrentPosition(success, error, options);
  }, []);

  return (
    <PageContainer>
      <div className="w-full h-full">
        <div className="flex justify-center items-center my-2">
          <h1 className="text-xl lg:text-2xl font-bold">편의점</h1>
        </div>
        <div className="w-full h-full">
          <Map latitude={latitude} longitude={longitude} />
        </div>
      </div>
    </PageContainer>
  );
}
