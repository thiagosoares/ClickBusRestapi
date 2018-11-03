package com.clickbus.service.service.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A DTO for the Place entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlaceDetailsDTO extends AbstractAuditingDto {

    private static final long serialVersionUID = 2346312361734613231L;

    private Long id;

    private String name;

    private String slug;

    private CityDTO city;
    
    private StateDTO state;
    
    private CountryDTO country;
    
    private Set<ClientApplicationDTO> clientIds = new HashSet<>(); 

}
