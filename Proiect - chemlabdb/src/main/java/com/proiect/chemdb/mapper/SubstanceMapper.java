package com.proiect.chemdb.mapper;

import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.dto.SubstanceDto;
import org.mapstruct.Mapper;

@Mapper
public interface SubstanceMapper extends EntityMapper<SubstanceDto, Substance> {
}
