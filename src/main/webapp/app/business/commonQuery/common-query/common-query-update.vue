<template>
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
        <k-form-build :value="formJsonData" ref="updateForm" :dynamicData="relationshipsData"/>
        <a-tabs defaultActiveKey="1">
            <a-tab-pane :tab="$t('jhiAntVueApp.commonQueryCommonQuery.items')" key="1">
                <jhi-common-query-item :showInOther="true" :queryId="commonQuery.id" :commonTableId="commonQuery.commonTableId" ref="itemListComponent"></jhi-common-query-item>
            </a-tab-pane>
        </a-tabs>
        <a-row type="flex" justify="center">
            <a-col span="3">
                <a-button @click="previousState()">{{$t('entity.action.cancel')}}</a-button>
            </a-col>
            <a-col span="3">
                <a-button type="primary" @click="save">{{$t('entity.action.save')}}</a-button>
            </a-col>
        </a-row>
    </a-card>
</template>
<script lang="ts" src="./common-query-update.component.ts">
</script>
<style>
    .ant-advanced-update-form .ant-form-item {
        display: flex;
    }
    .ant-advanced-update-form .ant-form-item-control-wrapper {
        flex: 1;
    }
</style>
