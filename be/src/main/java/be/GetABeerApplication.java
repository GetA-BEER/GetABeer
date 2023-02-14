package be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import be.domain.elasticsearch.repository.BeerSearchCustomRepositoryImpl;
import be.domain.elasticsearch.repository.BeerSearchRepository;

@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {BeerSearchRepository.class, BeerSearchCustomRepositoryImpl.class}
))
@SpringBootApplication
public class GetABeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetABeerApplication.class, args);
	}

}
