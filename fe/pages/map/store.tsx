import PageContainer from '@/components/PageContainer';
import { useEffect, useState } from 'react';
import MapNav from '@/components/map/MapNav';
import MapStore from '@/components/map/MapStore';

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
      setTimeout(() => {
        setLatitude(37.5699352);
        setLongitude(126.984834);
      }, 2000);
    }
    navigator.geolocation.getCurrentPosition(success, error, options);
  }, []);

  return (
    <PageContainer>
      <MapNav curTab={0} />
      <div className="w-full h-full">
        <div className="flex justify-end">
          <button
            className="text-xs rounded bg-y-cream text-y-brown p-0.5 mx-2 mb-2"
            onClick={() => {
              if (window !== undefined) {
                window.location.replace('/map/store');
              }
            }}
          >
            새로 고침
          </button>
        </div>
        <MapStore latitude={latitude} longitude={longitude} />
      </div>
    </PageContainer>
  );
}
