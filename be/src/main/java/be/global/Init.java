package be.global;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beercategory.repository.BeerCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class Init {

    private static final Logger log = LoggerFactory.getLogger(Init.class);

    @Bean
    @Transactional
    CommandLineRunner stubInit(BeerCategoryRepository beerCategoryRepository) {

        for (int i = 0; i < 7; i++) {
            BeerCategory beerCategory = BeerCategory.builder()
                    .beerCategoryType(BeerCategoryType.values()[i])
                    .build();
            beerCategoryRepository.save(beerCategory);
        }

        return null;
    }
}