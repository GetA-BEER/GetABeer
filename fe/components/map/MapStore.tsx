import { useEffect } from 'react';

export interface MapProps {
  latitude?: number;
  longitude?: number;
  hugeMode?: boolean;
}

declare global {
  interface Window {
    kakao: any;
  }
}

export default function MapStore({ latitude, longitude }: MapProps) {
  useEffect(() => {
    const mapScript = document.createElement('script');
    mapScript.async = true;
    mapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.NEXT_PUBLIC_KAKAO_API_KEY}&autoload=false&libraries=services,clusterer`;
    document.head.appendChild(mapScript);

    const onLoadKakaoMap = () => {
      window.kakao.maps.load(() => {
        const container = document.getElementById('map');
        const options = {
          center: new window.kakao.maps.LatLng(latitude, longitude),
          level: 4,
        };
        const map = new window.kakao.maps.Map(container, options);
        map.setMaxLevel(5);

        const customOverlay = new window.kakao.maps.CustomOverlay({
          xAnchor: 0.5,
          yAnchor: 1.8,
        });

        const ps = new window.kakao.maps.services.Places(map);
        ps.categorySearch('CS2', placesSearchCB, { useMapBounds: true });

        function placesSearchCB(data: any, status: any) {
          if (status === window.kakao.maps.services.Status.OK) {
            for (var i = 0; i < data.length; i++) {
              displayMarker(data[i]);
            }
          }
        }
        var imageSrc = '/images/marker.png', // 마커이미지의 주소입니다
          imageSize = new window.kakao.maps.Size(34, 39), // 마커이미지의 크기입니다
          imageOption = { offset: new window.kakao.maps.Point(16, 36) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        var markerImage = new window.kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imageOption
        );

        // 지도에 마커를 표시하는 함수입니다
        function displayMarker(place: any) {
          // 마커를 생성하고 지도에 표시합니다
          var marker = new window.kakao.maps.Marker({
            map: map,
            position: new window.kakao.maps.LatLng(place.y, place.x),
            image: markerImage,
          });

          // 마커에 클릭이벤트를 등록합니다
          window.kakao.maps.event.addListener(marker, 'click', function () {
            // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
            customOverlay.setPosition(
              new window.kakao.maps.LatLng(place.y, place.x)
            );
            customOverlay.setContent(
              '<div class="bg-y-cream text-xs rounded-md m-1 p-2 w-fit border-2 border-y-gold">' +
                place.place_name +
                '<button id="closeCustomOverlay" class="text-y-brown ml-2">x</button>' +
                '</div>'
            );
            customOverlay.setMap(map);
            const closeBtn = document.getElementById('closeCustomOverlay');
            closeBtn?.addEventListener('click', () => {
              customOverlay.setMap(null);
            });
          });
        }
      });
    };
    mapScript.addEventListener('load', onLoadKakaoMap);

    return () => mapScript.removeEventListener('load', onLoadKakaoMap);
  }, [latitude, longitude]);

  return <div id="map" className="w-full h-4/6"></div>;
}
