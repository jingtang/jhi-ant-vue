/**
 * JPA domain objects.
 */
@AnyMetaDef(
    idType = "long", metaType = "string",name = "ExtDataMetaDef",
    metaValues = {
        @MetaValue( targetEntity = CommonBoolean.class, value="CommonBoolean" ),
        @MetaValue( targetEntity = CommonFloat.class, value="CommonFloat" ),
        @MetaValue( targetEntity = CommonInteger.class, value="CommonInteger" ),
        @MetaValue( targetEntity = CommonLong.class, value="CommonLong" ),
        @MetaValue( targetEntity = CommonZonedDateTime.class, value="CommonZonedDateTime" ),
        @MetaValue( targetEntity = CommonString.class, value="CommonString" ),
        @MetaValue( targetEntity = CommonDouble.class, value="CommonDouble" )
    }
)
@AnyMetaDef(
    idType = "long", metaType = "string",name = "OwnerMetaDef",
    metaValues = {
        /* jhipster-needle-add-owner-meta-def - JHipster will add OwnerMetaDef */
    }
)
package com.aidriveall.cms.domain;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
