package com.proiect.chemdb.mapper;

import com.proiect.chemdb.domain.ConsumedSubstance;
import com.proiect.chemdb.dto.ConsumedSubstanceDto;
import org.mapstruct.Mapper;

@Mapper
public interface ConsumedSubstanceMapper extends EntityMapper <ConsumedSubstanceDto, ConsumedSubstance>{
}
