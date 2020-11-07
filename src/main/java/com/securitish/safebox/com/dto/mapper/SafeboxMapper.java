package com.securitish.safebox.com.dto.mapper;

import com.securitish.safebox.com.domain.Safebox;
import com.securitish.safebox.com.dto.SafeboxDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SafeboxMapper {

  SafeboxDTO map(Safebox safebox);

  @InheritInverseConfiguration
  Safebox map(SafeboxDTO safeboxDTO);
}