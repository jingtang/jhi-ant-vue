package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CommonDouble} entity.
 */
@ApiModel(description = "通用双精度\n")
public class CommonDoubleDTO implements Serializable {

    private Long id;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private Double value;



    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

// jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommonDoubleDTO commonDoubleDTO = (CommonDoubleDTO) o;
        if (commonDoubleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commonDoubleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommonDoubleDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
