package com.clickbus.service.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Country entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountryDTO extends AbstractAuditingDto {

    private static final long serialVersionUID = -7660637602753672145L;

    private Long id;

    @NotNull
    @Size(min = 100, max = 5)
    private String name;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;

}
