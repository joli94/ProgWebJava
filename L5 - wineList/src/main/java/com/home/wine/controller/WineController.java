package com.home.wine.controller;

import com.home.wine.domain.Wine;
import com.home.wine.dto.WineDto;
import com.home.wine.mapper.WineMapper;
import com.home.wine.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/wines")
public class WineController {
    private final WineService wineService;
    private final WineMapper wineMapper;

    @Autowired
    public WineController(WineService wineService, WineMapper wineMapper) {
        this.wineService = wineService;
        this.wineMapper = wineMapper;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<WineDto> wineDtos = wineService.getAllWines();
        model.addAttribute("wineDtos", wineDtos);
        return "view-wines";
    }

    @GetMapping("/{id}")
    public String getWineById(@PathVariable("id") String id, Model model) {
        WineDto wineDto = wineService.getWineById(id);
        model.addAttribute("wineDto", wineDto);
        return "view-wine";
    }

    @GetMapping("/view-create")
    public String viewCreate(WineDto wineDto) {
        return "add-wine";
    }

    @PostMapping("/create")
    public String createWine(@Valid WineDto wineDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-wine";
        }
        wineService.createWine(wineDto);
        model.addAttribute("wineDtos", wineService.getAllWines());
        return "view-wines";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable String id, WineDto wineDto, Model model) {
        WineDto currentWineDto = wineService.getWineById(id);
        model.addAttribute("wineDto", currentWineDto);
        return "update-wine";
    }

    @PostMapping("/update/{id}")
    public String updateWine(@PathVariable String id, @Valid WineDto wineDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-wine";
        }
        wineService.updateWine(wineDto);
        model.addAttribute("wineDto", wineService.getWineById(id));
        return "view-wine";
    }

    @GetMapping("/delete/{id}")
    public String deleteWine(@PathVariable String id, Model model) {
        wineService.delete(id);
        List<WineDto> wineDtos = wineService.getAllWines();
        model.addAttribute("wineDtos", wineDtos);
        return "view-wines";
    }
}
