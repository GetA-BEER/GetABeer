package be.domain.beerCategory.dto;

import be.domain.beerCategory.entity.BeerCategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class BeerCategoryDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long beerCategoryId;
        @NotBlank
        private BeerCategoryType beerCategoryType;
    }
}
