package be.global.statistics.analysis;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import tech.tablesaw.api.Table;

@Service
public class JTableSawService {

	public void test() throws IOException {

		String BASE_PATH = new File("").getAbsolutePath();
		String FILE_NAME = "test.csv";
		String FILE_PATH = BASE_PATH + "/" + FILE_NAME;

		Table tb = Table.read().csv(FILE_PATH);
		System.out.println(tb);
	}
}
