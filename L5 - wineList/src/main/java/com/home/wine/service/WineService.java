package com.home.wine.service;

import com.home.wine.domain.Wine;
import com.home.wine.dto.WineDto;
import com.home.wine.mapper.WineMapper;
import com.home.wine.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WineService {
    private final WineMapper wineMapper;
    private final WineRepository wineRepository;

    @Autowired
    public WineService(WineMapper wineMapper, WineRepository wineRepository) {
        this.wineMapper = wineMapper;
        this.wineRepository = wineRepository;
    }

    public List<WineDto> getAllWines(){
        return wineRepository.getAll().stream().map(wineMapper::convertFromWineToWineDto).collect(Collectors.toList());
    }

    public WineDto getWineById(String id){
        return wineMapper.convertFromWineToWineDto(wineRepository.getById(id).get());
    }

    public void createWine(WineDto wineDto) {
        wineRepository.save(wineMapper.convertFromWineDtoToWine(wineDto));
    }

    public void updateWine(WineDto wineDto) {
        Wine found = wineRepository.getById(wineDto.getWineId()).get();

        wineRepository.delete(found);
        wineRepository.save(wineMapper.convertFromWineDtoToWine(wineDto));
    }

    public void delete(String id) {
        Wine found = wineRepository.getById(id).get();
        wineRepository.delete(found);
    }
}
