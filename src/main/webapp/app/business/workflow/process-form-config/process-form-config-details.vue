<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="processFormConfig">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.workflowProcessFormConfig.detail.title')">ProcessFormConfig</span> {{processFormConfig.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessFormConfig.processDefinitionKey')">Process Definition Key</span>
                    </dt>
                    <dd>
                        <span>{{processFormConfig.processDefinitionKey}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessFormConfig.taskNodeId')">Task Node Id</span>
                    </dt>
                    <dd>
                        <span>{{processFormConfig.taskNodeId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessFormConfig.taskNodeName')">Task Node Name</span>
                    </dt>
                    <dd>
                        <span>{{processFormConfig.taskNodeName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessFormConfig.commonTableId')">Common Table Id</span>
                    </dt>
                    <dd>
                        <span>{{processFormConfig.commonTableId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessFormConfig.formData')">Form Data</span>
                    </dt>
                    <dd>
                        <span>{{processFormConfig.formData}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="processFormConfig.id" :to="{name: 'ProcessFormConfigEdit', params: {processFormConfigId: processFormConfig.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./process-form-config-details.component.ts">
</script>
