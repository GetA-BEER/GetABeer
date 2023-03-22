package be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableCaching
@EnableScheduling
@EnableJpaAuditing
// @EnableJpaRepositories
// 	(excludeFilters = @ComponentScan.Filter(
// 		type = FilterType.ASSIGNABLE_TYPE,
// 		classes = {BeerSearchRepository.class, BeerSearchCustomRepositoryImpl.class}
// 	))
// @EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60) // 레디스 세션 사용 설정
@SpringBootApplication
public class GetABeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetABeerApplication.class, args);
	}

}
