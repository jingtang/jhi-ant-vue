package com.aidriveall.cms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 通用日期时间
 * 
 */

@Entity
@Table(name = "common_zoned_date_time")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommonZonedDateTime implements Serializable, CommonField {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 值
     */
    @Column(name = "value")
    private ZonedDateTime value;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getValue() {
        return value;
    }

    public CommonZonedDateTime value(ZonedDateTime value) {
        this.value = value;
        return this;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonZonedDateTime)) {
            return false;
        }
        return id != null && id.equals(((CommonZonedDateTime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommonZonedDateTime{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
