package be.domain.gcp.Constant;

public class GcpConstant {
	public static final String PROJECT_ID = "getabeer-376509";
	public static final String COMPUTE_REGION = "asia-east1";
	public static final String PRODUCT_SET_ID = "beers";
	public static final String PRODUCT_SET_DISPLAY_NAME = "getabeer-product";
	public static final String PRODUCT_CATEGORY = "general-v1";

	public static final String[] PRODUCT_KORNAME =
		{"첫즙 라거", "애플 폭스", "아크 라거", "아사히 슈퍼 드라이", "바바리아 레몬", "베어 비어 라거", "벡스", "빅 웨이브", "블루걸", "블루문", "부데요비츠키 부드바르",
			"버드와이저", "카카오 S 스타우트 맥주", "카프리", "칼스버그", "카스 제로", "창 클래식", "창 에스프레소", "시메이 골드", "시메이 그랑리저브", "시메이 레드",
			"시메이 화이트", "쿠어스", "코로나 엑스트라", "콜센동크 파터", "크루져 라즈베리향", "컷워터 데킬라 마가리타", "J 에게 라거", "J 에게 복숭아 에일",
			"데슈츠 프레시 헤이즈", "데스페라도스 오리지널", "듀체스 드 부르고뉴", "덕덕구스 세션 아이피에이", "듀벨", "듀벨 6,66", "에델바이스", "에델바이스 피치",
			"에페스 필스너", "예거 자몽 라들러", "예거 복숭아 라들러", "예거 청포도 라들러", "예거 레몬 라들러", "에딩거 바이스비어", "에스트렐라 갈리시아", "에스트렐라 담",
			"파이어 락 페일 에일", "피즈 애플 사이다", "피즈 블루베리 사이다", "피즈 페어 사이다", "피즈 스트로베리 사이다", "가펠 쾰쉬", "가펠 레몬", "금강산 골든 에일",
			"고길동 에일", "골목 대장 골드 에일", "곰표 썸머 에일", "곰표 밀맥주", "구스 아이피에이", "구스 아일랜드 312 얼반 위트 에일", "그레벤슈타이너 오리지널",
			"그림버겐 블랑쉬", "기네스 엑스트라 스타우트", "기네스 드래프트", "하날레이 아일랜드 아이피에이", "핸드 앤 애플 사이더 사과맛", "핸드 앤 애플 사이더 수박 & 오이맛",
			"한옥마을 에일", "하얼빈", "하이네켄", "하이네켄 실버", "하이트 제로", "홉고블린 골드", "홉고블린 루비", "호가든", "호가든 보타닉", "호가든 프룻브루 페어",
			"호가든 프룻브루 로제", "호가든 논 알콜릭", "홉 하우스 13 라거", "인디카 아이피에이", "이네딧 담", "제주 거멍 에일", "제주 라거", "제주 펠롱 에일", "제주 슬라이스",
			"제주 위트 에일", "진달래 맥주", "천하장사 에너지 비어", "카이저돔 켈러비어", "카이저돔 필스너", "킹피셔맥주", "기린 이치방", "깡맥주 블랙", "깡맥주 오리지널",
			"클라우드 하드셀처 망고", "코나 롱보드 아일랜드 라거", "코젤 다크", "코젤 라거", "크롬바커 필스", "크롬바커 바이젠", "크로넨버그 1664", "크로넨버그 1664 블랑",
			"구미호 아이피에이", "구미호 피치 에일", "구미호 릴렉스 비어", "레페 블론드", "레페 브라운", "레오", "레츠 프레시 투데이", "린데만스 애플", "린데만스 크릭",
			"린데만스 뻬슈레제", "라이온 라거", "라이언 스타우트", "마쿠", "마쿠 블루베리", "마쿠 망고", "미켈롭 울트라", "머피스 아이리쉬 스타우트", "노스 코스트 올드 라스푸틴",
			"파카 하드셀처 블랙 체리", "파카 하드셀처 체리 라임", "파카 하드셀처 그레이프후루츠 텐저린", "파카 하드셀처 믹스 베리", "파타고니아 보헤미안 필스너", "파울라너 뮌헨 라거",
			"파울라너 바이스비어", "파울라너 바이스비어 둔켈", "페로니 나스트라즈로", "필스너 우르켈", "에일의 정석", "리퍼비 인디아 페일 에일", "사무엘 아담스 보스턴 라거", "산미구엘",
			"산 미구엘 세르베자 블랑카", "산미구엘 NAB", "삿포로 프리미엄", "쉐퍼 호퍼 그레이프후르트", "석빙고 라거", "서울숲 필스너", "싱하", "스마일리 맥주", "스마일리 몰디브",
			"써머스비 애플", "SSG 랜더스 라거", "스텔라 아르투아", "슈티글 골드브로이", "슈티글 그레이프 프루트", "슈티글 시트론 라들러", "스톤 아이피에이", "산토리 프리미엄 몰츠",
			"슈퍼스타즈 페일 에일", "따상주", "템트 7", "템트 9", "강철부대 맥주", "타이거", "타이거 라들러 그레이프 프루트", "타이거 라들러 레몬", "컷워터 티키 럼 마이타이",
			"트롤브루 자몽", "트롤브루 레몬", "칭다오", "칭다오 퓨어 드래프트", "칭따오 스타우트", "칭타오 바이젠 위트비어", "노을 수제 에일", "컷워터 보드카 소다", "볼파스 엔젤맨",
			"볼파스 엔젤맨 블랑", "볼파스 엔젤맨 헤페바이젠", "볼파스 엔젤맨 아이피에이", "바르슈타이너 프리미엄 필스너", "바이엔슈테판 헤페 바이스비어", "바이엔슈테판 헤페 바이스비어 둔켈",
			"바이엔슈테판 크리스탈 바이스비어", "에비스", "골목 대장", "오늘 맥주"};

	public static final String[] PRODUCT_ENGNAME =
		{"1ST WORT LAGER", "APPLE FOX", "ARK LAGER", "ASAHI SUPER DRY", "BAVARIA LEMON", "BEAR BEER LAGER", "BECK’S",
			"BIG WAVE", "BLUE GIRL", "BLUE MOON", "BUDEJOVICKY BUDVAR", "BUDWEISER", "CACAO S STOUT BEER", "CAFRI",
			"CARLSBERG", "CASS ZERO", "CHANG CLASSIC", "CHANG ESPRESSO LAGER", "CHIMAY GOLD", "CHIMAY GRANDE RESERVE",
			"CHIMAY PERES TRAPPISTES", "CHIMAY PERES TRAPPISTES TRIPLE", "COORS", "CORONA EXTRA", "CORSENDONK PATER",
			"CRUISER RASPBERRY", "CUTWATER TEQUILA MARGARITA", "DEAR J LAGER", "DEAR J PEACH ALE",
			"DESCHUTES FRESH HAZE", "DESPRADOS ORIGINAL", "DUCHESSE DE BOURGOGNE", "DUCK DUCK GOOSE SESSION IPA",
			"DUVEL", "DUVEL 6,66", "EDELWEISS", "EDELWEISS PEACH", "EFES PILSNER", "EGGER GRAPEFRUIT RADLER",
			"EGGER PFIRSICH RADLER", "EGGER TRAUBEN RADLER", "EGGER ZITRONEN RADLER", "ERDINGER WEISSBIER",
			"ESTELLA GALICIA", "ESTRELLA DAMM", "FIRE ROCK PALE ALE", "FIZZ CIDER APPLE", "FIZZ CIDER BLUEBERRY",
			"FIZZ CIDER PEAR", "FIZZ CIDER STRAWBERRY", "GAFFEL KOLSCH", "GAFFEL LEMON", "GEUMGANGSAN GOLDEN ALE",
			"GOGILDONG ALE", "GOLMOG DAEJANG GOLD ALE", "GOMPYO SUMMER ALE", "GOMPYO WHEAT BEER", "GOOSE IPA",
			"GOOSE ISLAND 312 URBAN WHEAT ALE", "GREVENSTEINER ORIGINAL", "GRIMBERGEN BLANCHE", "GUINESS EXTRA STOUT",
			"GUINNESS DRAUGHT", "HANALEI ISLAND IPA", "HAND & APPLE CIDER APPLE",
			"HAND & APPLE CIDER WATERMELON & CUCUMBER", "HANOK VILLAGE ALE", "HARBIN", "HEINEKEN", "HEINEKEN SILVER",
			"HITE ZERO", "HOBGOBLIN GOLD", "HOBGOBLIN RUBY", "HOEGAARDEN", "HOEGAARDEN BOTANIC", "HOEGAARDEN PEAR",
			"HOEGAARDEN ROSE", "HOEGAARDEN NON_ALCOHOLIC", "HOP HOUSE 13 LAGER", "INDICA IPA", "INEDIT DAMM",
			"JEJU GEOMEONG ALE", "JEJU LAGER", "JEJU PELLONG ALE", "JEJU SLICE", "JEJU WIT ALE", "JIN DA LAI BEER",
			"KABREW ENERGY BEER", "KAISERDOM KELLERBIER", "KAISERDOM PILSENER", "KINGFISHER PREMIUM LAGER BEER",
			"KIRIN ICHBAN", "KKANG BEER BLACK", "KKANG BEER ORIGINAL", "KLOUD HARD SELTZER MANGO",
			"KONA LONGBOARD ISLAND LAGER", "KOZEL DARK", "KOZEL LAGEL", "KROMBACHER PILS", "KROMBACHER WEIZEN",
			"KRONENBOURG 1664", "KRONENBOURG 1664 BLANC", "KUMIHO IPA", "KUMIHO PEACH ALE", "KUMIHO RELAX BEER",
			"LEFFE BLOND", "LEFFE BRUNE", "LEO", "LETS FRESH TODAY", "LINDEMANS APPLE", "LINDEMANS KRIEK",
			"LINDEMANS PECHERESSE", "LION LAGER", "LION STOUT", "MAKKU", "MAKKU BLUEBERRY", "MAKKU MANGO",
			"MICHELOB ULTRA", "MURPHY’S IRISH STOUT", "NORTH COAST OLD RASPUTIN RUSSIAN IMPERIAL STOUT",
			"PAKKA HARD SELTZER BLACK CHERRY", "PAKKA HARD SELTZER CHERRY LIME",
			"PAKKA HARD SELTZER GRAPEFRUIT TANGERINE", "PAKKA HARD SELTZER MIXED BERRY", "PATAGONIA BOHEMIAN PILSENER",
			"PAULANER MUNICHI LAGER", "PAULANER WEISSBIER", "PAULANER WEISSBIER DUNKEL", "PERONI NASTRO AZZURRO",
			"PILSMER URQUELL", "PLATINUM STANDARD OF ALE", "REEPER B. INDIA PALE ALE", "SAMUEL ADAMS BOSTON LAGER",
			"SAN MIGUEL", "SAN MIGUEL CERVEZA BLANCA", "SAN MIGUEL NAB", "SAPPORO PREMIUM",
			"SCHOEFFER HOFER GRAPEFRUIT", "SEOKBINGO LAGER BEER", "SEOUL FOREST PILSNER", "SINGHA", "SMILEY BEER",
			"SMILEY MALDIVES", "SOMERSBY APPLE", "SSG LANDERS LAGER", "STELLA ARTOIS", "STIEGL GOLDBRAU",
			"STIEGL GRAPERUIT", "STIEGL RADLER ZITRONE", "STONE IPA", "SUNTORY PREMIUM MALTS", "SUPER STARS PALE ALE",
			"TASANGJU", "TEMPT NO 7", "TEMPT NO 9", "THE IRON SQUAD BEER", "TIGER", "TIGER RADLER GRAPEFRUIT",
			"TIGER RADLER LEMON", "TIKI RUM MAI TAI", "TROLL BREW HEFE GRAPEFRUIT", "TROLL BREW LEMON RADLER",
			"TSINGTAO", "TSINGTAO PURE DRAFT", "TSINGTAO STOUT", "TSINGTAO WHEAT BEER", "TWILGHT AMERICAN PALE ALE",
			"VODCA SODA", "VOLFAS ENGELMAN", "VOLFAS ENGELMAN BLANC", "VOLFAS ENGELMAN HEFEWEIZEN",
			"VOLFAS ENGELMAN IPA", "WARSTEINER PREMIUM PILSNER", "WEIHENSTEPHAN HEFEWEISSBIER",
			"WEIHENSTEPHAN HEFEWEISSBIER DUNKEL", "WEIHENSTEPHAN KRISTALL WEISSBIER", "YEBISU", "YOUNG BOSS",
			"ONUEL BEER"};

}
