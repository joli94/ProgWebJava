package com.home.wine.repository;

import com.home.wine.domain.Wine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WineRepository implements InitializingBean {

    private final List<Wine> wineList = new ArrayList<>();

    public List<Wine> getAll(){
        return wineList;
    }

    public Optional<Wine> getById(String id) {
        return wineList.stream().filter(wine -> wine.getId().equals(id)).findFirst();
    }

    private void createAndSave(String bottles, String year, String color, String id, String name, String taste) {
        Wine wine = Wine.builder()
                .bottles(bottles)
                .year(year)
                .color(color)
                .id(id)
                .name(name)
                .taste(taste)
                .build();

        wineList.add(wine);
    }

    public void save(Wine wine) { wineList.add(wine); }

    public void delete(Wine wine) {
        wineList.remove(wine);
    }

    private void setContextForWineRepository() {
        createAndSave("50","2019", "red", "1", "Pinot Noir", "dry");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setContextForWineRepository();
    }
}
