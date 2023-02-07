package be.domain.crawler.javaCrawler;

import java.util.ArrayList;
import java.util.List;

public class SeleniumConstant {

	public static final String START_URL = "https://www.ratebeer.com/beer/asda-whitechapel-porter/22849";
	public static final String SEARCH_BOX = "/html/body/div[1]/div[2]/header/div[2]/div[1]/div[2]/div/div/input";
	public static final String FIRST_RESULT = "//*[@id=\"root\"]/div[2]/header/div[2]/div[1]/div[2]/div/div[2]/Test[1]";
	public static final String FIRST_RESULT_IMAGE = "//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[2]/div[1]/Test/div[1]/div/img";
	public static final String BEER_INFO_XPATH = "//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]";
	public static List<String> NAME_LIST = new ArrayList<>(
		List.of("kloud", "fitz super clear", "Asahi super dry", "Tsingtao", "Heineken",
			"Kirin ichiban", "Sapporo Premium Beer / Draft Beer", "stella artois", "guinness braught",
			"1664 Blanc", "pilsner urquell", "San Miguel", "OB premier pilsner", "cass fresh",
			"stout", "dry finish", "max hite", "hite extra cold", "victoria bitter", "BINTANG pilsner",
			"krombacher weizen", "Miller Genuine Draft", "Hoegaarden Cherry", "TIGER REDLER",
			"Suntory The Premium Malt's", "REEPER B Weissbier", "PEEPER B IPA", "TIGER BEER",
			"TSINGTAO WHEAT BEER", "Erdinger Weissbier", "Carlsberg", "Budweiser ", "Hoegarden",
			"YEBISU", "Paulaner Hefe-weissbier", "Desperados", "Peroni Nastro Azzurro",
			"Edelweiss Snowfresh", "Heineken Dark Lager", "Kozel Dark 10", "Guinness original",
			"Filite", "SEOULITE ALE", "JEJU WIT ALE", "Stephans Brau Philsner", "Stephans Brau Larger"));

	public static final List<String> TEST_LIST = new ArrayList<>(
		List.of("1664 Blanc", "pilsner urquell", "San Miguel")
	);

}
