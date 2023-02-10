package be.global.statistics.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.FlyCapture2;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.global.statistics.entity.BeerStatistics;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BeerStatisticsJdbcRepository {
	private final JdbcTemplate jdbcTemplate;
	private Integer batchSize = 10000;

	public void saveAll(List<BeerStatistics> beerStatisticsList) {

		Integer batchCount = 0;

		List<BeerStatistics> subList = new ArrayList<>();

		for (int i = 0; i < beerStatisticsList.size(); i++) {

			subList.add(beerStatisticsList.get(i));

			if ((i + 1) % batchSize == 0) {
				batchCount = batchInsert(batchCount, subList);
			}
		}
		if (!subList.isEmpty()) {
			batchCount = batchInsert(batchCount, subList);
		}
		System.out.println("batchCount: " + batchCount);
	}

	private Integer batchInsert(Integer batchCount, List<BeerStatistics> subList) {

		jdbcTemplate.batchUpdate("INSERT INTO BEER_STATISTICS (create_at, date, beer_id, kor_name,"
				+ "category1, category2, total_average_stars, female_average_stars, male_average_stars, tag1,"
				+ "tag2, tag3, tag4, view_count, rating_count)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
					BeerStatistics beerStatistics = subList.get(i);
					preparedStatement.setTimestamp(1, Timestamp.valueOf(beerStatistics.getCreatedAt()));
					preparedStatement.setInt(2, beerStatistics.getWeek());
					preparedStatement.setLong(3, beerStatistics.getBeerId());
					preparedStatement.setString(3, beerStatistics.getKorName());
					preparedStatement.setString(3, beerStatistics.getCategory1());
					preparedStatement.setString(3, beerStatistics.getCategory2());
					preparedStatement.setDouble(3, beerStatistics.getBeerDetailsStars().getTotalAverageStars());
					preparedStatement.setDouble(3, beerStatistics.getBeerDetailsStars().getFemaleAverageStars());
					preparedStatement.setDouble(3, beerStatistics.getBeerDetailsStars().getMaleAverageStars());
					preparedStatement.setString(3, beerStatistics.getBeerDetailsTopTags().getTag1());
					preparedStatement.setString(3, beerStatistics.getBeerDetailsTopTags().getTag2());
					preparedStatement.setString(3, beerStatistics.getBeerDetailsTopTags().getTag3());
					preparedStatement.setString(3, beerStatistics.getBeerDetailsTopTags().getTag4());
					preparedStatement.setLong(3, beerStatistics.getViewCount());
					preparedStatement.setLong(3, beerStatistics.getRatingCount());
				}

				@Override
				public int getBatchSize() {
					return subList.size();
				}
			});
		subList.clear();
		batchCount++;
		return batchCount;
	}
}
