<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="processTableConfig">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.detail.title')">ProcessTableConfig</span> {{processTableConfig.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.processDefinitionId')">Process Definition Id</span>
                    </dt>
                    <dd>
                        <span>{{processTableConfig.processDefinitionId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.processDefinitionKey')">Process Definition Key</span>
                    </dt>
                    <dd>
                        <span>{{processTableConfig.processDefinitionKey}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.processDefinitionName')">Process Definition Name</span>
                    </dt>
                    <dd>
                        <span>{{processTableConfig.processDefinitionName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{processTableConfig.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.processBpmnData')">Process Bpmn Data</span>
                    </dt>
                    <dd>
                        <span>{{processTableConfig.processBpmnData}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.deploied')">Deploied</span>
                    </dt>
                    <dd>
                        <span>{{processTableConfig.deploied}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.commonTable')">关联表</span>
                    </dt>
                    <dd>
                        <div v-if="processTableConfig.commonTableId">
                            <router-link :to="{name: 'CommonTableView', params: {commonTableId: processTableConfig.commonTableId}}">{{processTableConfig.commonTableName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessTableConfig.creator')">创建人</span>
                    </dt>
                    <dd>
                        {{processTableConfig.creatorLogin}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="processTableConfig.id" :to="{name: 'ProcessTableConfigEdit', params: {processTableConfigId: processTableConfig.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./process-table-config-details.component.ts">
</script>
