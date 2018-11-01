package com.clickbus.service.service.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A DTO for the ClientApplication entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientApplicationDTO extends AbstractAuditingDto {

    private static final long serialVersionUID = -2196473443547343174L;

    private Long id;

    @NotNull
    @Size(min = 100, max = 5)
    private String name;

    @NotNull
    private String publicName;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;

}
