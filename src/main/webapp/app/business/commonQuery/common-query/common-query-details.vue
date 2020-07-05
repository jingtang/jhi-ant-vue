<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="commonQuery">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.commonQueryCommonQuery.detail.title')">CommonQuery</span> {{commonQuery.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQuery.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{commonQuery.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQuery.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{commonQuery.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQuery.lastModifiedTime')">Last Modified Time</span>
                    </dt>
                    <dd>
                        <span v-if="commonQuery.lastModifiedTime">{{$d(Date.parse(commonQuery.lastModifiedTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQuery.modifier')">编辑人</span>
                    </dt>
                    <dd>
                        {{commonQuery.modifierFirstName}}
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQuery.commonTable')">所属模型</span>
                    </dt>
                    <dd>
                        <div v-if="commonQuery.commonTableId">
                            <router-link :to="{name: 'CommonTableView', params: {commonTableId: commonQuery.commonTableId}}">{{commonQuery.commonTableName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="commonQuery.id" :to="{name: 'CommonQueryEdit', params: {commonQueryId: commonQuery.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./common-query-details.component.ts">
</script>
