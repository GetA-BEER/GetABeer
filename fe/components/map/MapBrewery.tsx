import { useEffect } from 'react';
import { MapProps } from './MapStore';

declare global {
  interface Window {
    kakao: any;
  }
}

export default function MapBrewery({
  latitude,
  longitude,
  hugeMode,
}: MapProps) {
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
          level: 13,
        };
        const map = new window.kakao.maps.Map(container, options);
        const customOverlay = new window.kakao.maps.CustomOverlay({
          xAnchor: 0.5,
          yAnchor: 1.8,
        });

        if (hugeMode) {
          map.setLevel(13);
        } else {
          map.setLevel(4);
        }

        // 마커 클러스터러를 생성합니다
        var clusterer = new window.kakao.maps.MarkerClusterer({
          map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
          averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
          minLevel: 8, // 클러스터 할 최소 지도 레벨
        });
        var imageSrc = '/images/marker.png', // 마커이미지의 주소입니다
          imageSize = new window.kakao.maps.Size(34, 39), // 마커이미지의 크기입니다
          imageOption = { offset: new window.kakao.maps.Point(16, 36) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        var markerImage = new window.kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imageOption
        );

        //json 파일 가져오기
        function showBreweryData() {
          const breweryData = require('./BreweryInfo.json');
          const markers = breweryData.position?.map(function (
            position: any,
            i: any
          ) {
            const marker = new window.kakao.maps.Marker({
              position: new window.kakao.maps.LatLng(
                position.latitude,
                position.longitude
              ),
              image: markerImage,
            });
            window.kakao.maps.event.addListener(marker, 'click', function () {
              customOverlay.setPosition(
                new window.kakao.maps.LatLng(
                  position.latitude,
                  position.longitude
                )
              );
              customOverlay.setContent(
                '<div class="bg-y-cream text-xs rounded-md m-1 p-2 w-fit border-2 border-y-gold z-10">' +
                  '<div class="title flex justify-between items-center">' +
                  position.name +
                  '<button id="closeCustomOverlay" class="text-y-brown ml-2">x</button>' +
                  ' </div>' +
                  '<div class="body font-thin">' +
                  position.address +
                  '</div>' +
                  '</div>'
              );
              customOverlay.setMap(map);
              const closeBtn = document.getElementById('closeCustomOverlay');
              closeBtn?.addEventListener('click', () => {
                customOverlay.setMap(null);
              });
            });
            return marker;
          });
          clusterer.addMarkers(markers);
        }
        showBreweryData();
      });
    };
    mapScript.addEventListener('load', onLoadKakaoMap);

    return () => mapScript.removeEventListener('load', onLoadKakaoMap);
  }, [latitude, longitude, hugeMode]);
  return <div id="map" className="aspect-square lg:aspect-video"></div>;
}
