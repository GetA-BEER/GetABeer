package be.global.statistics.analysis;

import static tech.tablesaw.aggregate.AggregateFunctions.*;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.api.VerticalBarPlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.traces.BarTrace;

@Service
public class JTableSawService {

	private final String BASE_PATH = new File("").getAbsolutePath();
	private final String FILE_NAME = "test2.csv";
	private final String FILE_PATH = BASE_PATH + "/" + FILE_NAME;

	public void statistics() throws IOException {

		Table tb = Table.read().csv(FILE_PATH);

		System.out.println(tb);

		// System.out.println(tb);
		// System.out.println(tb.first(20)); // 처음 20 로우만
		// System.out.println(tb.last(20)); // 마지막 20 로우만
		// System.out.println(tb.shape()); // 테이블 모양
		// System.out.println(tb.columnNames()); // 컬럼 이름들
		// System.out.println(tb.structure()); // 컬럼 데이터 타입
		// System.out.println(tb.column("시군구").summary());
		// System.out.println(tb.summarize("수급권자수", max, min, mean, median).by("시군구"));
		// System.out.println(tb.summarize("수급권자수", max).by("시군구"));
		// System.out.println(tb.summarize("수급권자수", max, min, mean, median).apply());
		// System.out.println(tb.summary());
		// System.out.println(tb.sortDescendingOn("수급권자수"));

		// Table table = tb.summarize("수급권자수", mean).by("시군구");
	}

	public void visualization() throws IOException {

		Table tb = Table.read().csv(FILE_PATH);

		Table table = tb.summarize("수급권자수", mean)
			.by("시군구").sortDescendingOn("Mean [수급권자수]");

		/*
		 * 막대그래프 (가로)
		 */
		Plot.show(
			HorizontalBarPlot.create(
				"시군구 수급권자 수 평균",
				table,
				"시군구",
				"mean [수급권자수]"
			)
		);

		// Table table2 = tb.summarize("수급권자수", max).by("시군구").sortDescendingOn("Max [수급권자수]");

		/*
		 * 막대그래프 (세로)
		 */
		Plot.show(
			VerticalBarPlot.create(
				"시군구 수급권자 수 최대값",
				table,
				"시군구",
				"Mean [수급권자수]"
			)
		);

		/*
		 * 원형 그래프
		 */
		Plot.show(
			PiePlot.create(
				"시군구 수급권자 수 평균",
				table,
				"시군구",
				"Mean [수급권자수]"
			)
		);

		Table table2 = tb.summarize("수급권자수", mean).by("시군구");

		table2 = table2.sortDescendingOn(table2.column(1).name());
		Layout layout = Layout.builder().title("시군구 수급권자 수 평균").build();
		BarTrace barTrace = BarTrace.builder(table.categoricalColumn(0), table2.numberColumn(1)).build();
		Plot.show(new Figure(layout, barTrace));
	}

	public void scatterPlot() throws IOException {

		String BASE_PATH = new File("").getAbsolutePath();
		String FILE_NAME = "test.csv";
		String FILE_PATH = BASE_PATH + "/" + FILE_NAME;

		Table tb = Table.read().csv(FILE_PATH);

		System.out.println(tb.selectColumns("위도", "경도"));

		Table table = tb.selectColumns("위도", "경도");

		Plot.show(
			ScatterPlot.create("노인 장애인 보호구역", table, "경도", "위도")
		);
	}
}
