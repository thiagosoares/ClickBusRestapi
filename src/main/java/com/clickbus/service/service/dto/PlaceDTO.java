package com.clickbus.service.service.dto;

import java.time.Instant;
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
public class PlaceDTO extends AbstractAuditingDto {

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

    private Set<ClientApplicationDTO> clientApplications = new HashSet<>();

    private Long cityId;

}
