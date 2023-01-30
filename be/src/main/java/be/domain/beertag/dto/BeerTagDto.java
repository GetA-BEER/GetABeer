package be.domain.beertag.dto;

import be.domain.beertag.entity.BeerTagType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BeerTagDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long beerTagId;
        private BeerTagType beerTagType;
    }
}
