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
    @Size(min = 255, max = 5)
    private String name;

    @NotNull
    private String terminalName;

    @NotNull
    private String address;

    @NotNull
    private String slug;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;

    private Set<ClientApplicationDTO> clientApplications = new HashSet<>();

    private Long cityId;

}
