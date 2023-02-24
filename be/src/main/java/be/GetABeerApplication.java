package be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableJpaAuditing
// @EnableJpaRepositories
// 	(excludeFilters = @ComponentScan.Filter(
// 		type = FilterType.ASSIGNABLE_TYPE,
// 		classes = {BeerSearchRepository.class, BeerSearchCustomRepositoryImpl.class}
// 	))

@SpringBootApplication
public class GetABeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetABeerApplication.class, args);
	}

}
