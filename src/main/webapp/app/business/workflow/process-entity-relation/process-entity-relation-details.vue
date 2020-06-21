<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="processEntityRelation">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.workflowProcessEntityRelation.detail.title')">ProcessEntityRelation</span> {{processEntityRelation.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessEntityRelation.entityType')">Entity Type</span>
                    </dt>
                    <dd>
                        <span>{{processEntityRelation.entityType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessEntityRelation.entityId')">Entity Id</span>
                    </dt>
                    <dd>
                        <span>{{processEntityRelation.entityId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessEntityRelation.processInstanceId')">Process Instance Id</span>
                    </dt>
                    <dd>
                        <span>{{processEntityRelation.processInstanceId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowProcessEntityRelation.status')">Status</span>
                    </dt>
                    <dd>
                        <span v-text="$t('jhiAntVueApp.ProcessEntityStatus.' + processEntityRelation.status)">{{processEntityRelation.status}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="processEntityRelation.id" :to="{name: 'ProcessEntityRelationEdit', params: {processEntityRelationId: processEntityRelation.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./process-entity-relation-details.component.ts">
</script>
