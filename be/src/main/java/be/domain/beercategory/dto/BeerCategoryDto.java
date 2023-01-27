package be.domain.beercategory.dto;

import be.domain.beercategory.entity.BeerCategoryType;
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
