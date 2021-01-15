package com.proiect.chemdb.mapper;

import com.proiect.chemdb.domain.BuySubstance;
import com.proiect.chemdb.dto.BuySubstanceDto;
import org.mapstruct.Mapper;

@Mapper
public interface BuySubstanceMapper extends EntityMapper <BuySubstanceDto, BuySubstance>{
}
