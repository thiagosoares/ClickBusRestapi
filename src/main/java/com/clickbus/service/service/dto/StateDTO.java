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
 * A DTO for the State entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StateDTO extends AbstractAuditingDto {

    private static final long serialVersionUID = -8092521575764317559L;

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

    private Long countryId;

}
