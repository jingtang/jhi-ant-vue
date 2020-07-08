<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="commonQueryItem">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.detail.title')">CommonQueryItem</span> {{commonQueryItem.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.prefix')">Prefix</span>
                    </dt>
                    <dd>
                        <span>{{commonQueryItem.prefix}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.fieldName')">Field Name</span>
                    </dt>
                    <dd>
                        <span>{{commonQueryItem.fieldName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.fieldType')">Field Type</span>
                    </dt>
                    <dd>
                        <span>{{commonQueryItem.fieldType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.operator')">Operator</span>
                    </dt>
                    <dd>
                        <span>{{commonQueryItem.operator}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{commonQueryItem.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.suffix')">Suffix</span>
                    </dt>
                    <dd>
                        <span>{{commonQueryItem.suffix}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.order')">Order</span>
                    </dt>
                    <dd>
                        <span>{{commonQueryItem.order}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.commonQueryCommonQueryItem.query')">查询</span>
                    </dt>
                    <dd>
                        <div v-if="commonQueryItem.queryId">
                            <router-link :to="{name: 'CommonQueryView', params: {commonQueryId: commonQueryItem.queryId}}">{{commonQueryItem.queryName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="commonQueryItem.id" :to="{name: 'CommonQueryItemEdit', params: {commonQueryItemId: commonQueryItem.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./common-query-item-details.component.ts">
</script>
