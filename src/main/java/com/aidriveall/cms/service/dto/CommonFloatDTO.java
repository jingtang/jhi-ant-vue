package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CommonFloat} entity.
 */
@ApiModel(description = "通用单精度\n")
public class CommonFloatDTO implements Serializable {

    private Long id;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private Float value;



    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
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

        CommonFloatDTO commonFloatDTO = (CommonFloatDTO) o;
        if (commonFloatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commonFloatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommonFloatDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
