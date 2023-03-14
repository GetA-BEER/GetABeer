import PageContainer from '@/components/PageContainer';
import { useEffect, useState } from 'react';
import MapNav from '@/components/map/MapNav';
import MapBrewery from '@/components/map/MapBrewery';

export default function BreweryMap() {
  const [latitude, setLatitude] = useState<number>(37.5699352);
  const [longitude, setLongitude] = useState<number>(126.984834);
  const [hugeMode, setHugeMode] = useState<boolean>(true);
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
      <MapNav curTab={1} />
      <div className="w-full h-full">
        <div className="flex justify-end">
          {hugeMode ? (
            <button
              className="text-xs rounded bg-y-cream text-y-brown p-0.5 mx-2 mb-2"
              onClick={() => {
                setHugeMode(!hugeMode);
              }}
            >
              내 위치로
            </button>
          ) : (
            <button
              className="text-xs rounded bg-y-cream text-y-brown p-0.5 mx-2 mb-2"
              onClick={() => {
                setHugeMode(!hugeMode);
              }}
            >
              전국 보기
            </button>
          )}
          <button
            className="text-xs rounded bg-y-cream text-y-brown p-0.5 mx-2 mb-2"
            onClick={() => {
              if (window !== undefined) {
                window.location.replace('/map/brewery');
              }
            }}
          >
            새로고침
          </button>
        </div>
        <MapBrewery
          latitude={latitude}
          longitude={longitude}
          hugeMode={hugeMode}
        />
      </div>
    </PageContainer>
  );
}
