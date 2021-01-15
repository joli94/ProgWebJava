package com.home.wine.mapper;

import com.home.wine.domain.Wine;
import com.home.wine.dto.WineDto;
import org.springframework.stereotype.Component;

@Component
public class WineMapper {
    public Wine convertFromWineDtoToWine(WineDto wineDto) {
        return Wine.builder()
                .bottles(wineDto.getWineBottles())
                .year(wineDto.getWineYear())
                .color(wineDto.getWineColor())
                .id(wineDto.getWineId())
                .name(wineDto.getWineName())
                .taste(wineDto.getWineTaste())
                .build();
    }

    public WineDto convertFromWineToWineDto(Wine wine) {
        return WineDto.builder()
                .wineBottles(wine.getBottles())
                .wineYear(wine.getYear())
                .wineColor(wine.getColor())
                .wineId(wine.getId())
                .wineName(wine.getName())
                .wineTaste(wine.getTaste())
                .build();
    }
}
