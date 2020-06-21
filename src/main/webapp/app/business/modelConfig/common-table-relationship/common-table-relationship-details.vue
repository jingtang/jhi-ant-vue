<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="commonTableRelationship">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.detail.title')">CommonTableRelationship</span> {{commonTableRelationship.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.relationshipType')">Relationship Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('jhiAntVueApp.RelationshipType.' + commonTableRelationship.relationshipType)">{{commonTableRelationship.relationshipType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.sourceType')">Source Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('jhiAntVueApp.SourceType.' + commonTableRelationship.sourceType)">{{commonTableRelationship.sourceType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.otherEntityField')">Other Entity Field</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.otherEntityField}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.otherEntityName')">Other Entity Name</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.otherEntityName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.relationshipName')">Relationship Name</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.relationshipName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.otherEntityRelationshipName')">Other Entity Relationship Name</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.otherEntityRelationshipName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.columnWidth')">Column Width</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.columnWidth}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.order')">Order</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.order}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.fixed')">Fixed</span>
                    </dt>
                    <dd>
                        <span v-text="$t('jhiAntVueApp.FixedType.' + commonTableRelationship.fixed)">{{commonTableRelationship.fixed}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.editInList')">Edit In List</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.editInList}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.enableFilter')">Enable Filter</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.enableFilter}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.hideInList')">Hide In List</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.hideInList}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.hideInForm')">Hide In Form</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.hideInForm}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.fontColor')">Font Color</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.fontColor}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.backgroundColor')">Background Color</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.backgroundColor}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.help')">Help</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.help}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.ownerSide')">Owner Side</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.ownerSide}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.dataName')">Data Name</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.dataName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.webComponentType')">Web Component Type</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.webComponentType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.otherEntityIsTree')">Other Entity Is Tree</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.otherEntityIsTree}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.showInFilterTree')">Show In Filter Tree</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.showInFilterTree}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.dataDictionaryCode')">Data Dictionary Code</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.dataDictionaryCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.clientReadOnly')">Client Read Only</span>
                    </dt>
                    <dd>
                        <span>{{commonTableRelationship.clientReadOnly}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.relationEntity')">关联实体</span>
                    </dt>
                    <dd>
                        <div v-if="commonTableRelationship.relationEntityId">
                            <router-link :to="{name: 'CommonTableView', params: {commonTableId: commonTableRelationship.relationEntityId}}">{{commonTableRelationship.relationEntityName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.dataDictionaryNode')">字典表节点</span>
                    </dt>
                    <dd>
                        <div v-if="commonTableRelationship.dataDictionaryNodeId">
                            <router-link :to="{name: 'DataDictionaryView', params: {dataDictionaryId: commonTableRelationship.dataDictionaryNodeId}}">{{commonTableRelationship.dataDictionaryNodeName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.modelConfigCommonTableRelationship.commonTable')">所属表</span>
                    </dt>
                    <dd>
                        <div v-if="commonTableRelationship.commonTableId">
                            <router-link :to="{name: 'CommonTableView', params: {commonTableId: commonTableRelationship.commonTableId}}">{{commonTableRelationship.commonTableName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="commonTableRelationship.id" :to="{name: 'CommonTableRelationshipEdit', params: {commonTableRelationshipId: commonTableRelationship.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./common-table-relationship-details.component.ts">
</script>
