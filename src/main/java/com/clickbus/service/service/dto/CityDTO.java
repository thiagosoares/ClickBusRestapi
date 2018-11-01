package com.clickbus.service.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the City entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CityDTO extends AbstractAuditingDto {

    private static final long serialVersionUID = -6760804659385009864L;

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

    private Long stateId;
   
}
