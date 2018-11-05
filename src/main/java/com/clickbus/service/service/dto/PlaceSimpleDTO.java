package com.clickbus.service.service.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.clickbus.service.domain.City;
import com.clickbus.service.domain.ClientApplication;
import com.clickbus.service.service.dto.projections.PlaceProjectionsDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the Place entity.
 */
@Data 
@EqualsAndHashCode(callSuper = true, exclude = "clientApplications")
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name", "terminalName", "address", "slug"})
public class PlaceSimpleDTO extends AbstractAuditingDto implements PlaceProjectionsDTO {

    private static final long serialVersionUID = 2346312361734613231L;

    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Size(min = 2, max = 255)
    private String terminalName;

    @NotNull
    private String address;

    @NotNull
    @Size(min = 2, max = 255)
    private String slug;

}
