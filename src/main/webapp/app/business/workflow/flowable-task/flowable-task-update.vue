<template>
    <!--<a-card :body-style="{padding: '24px 32px'}" :bordered="false">
        <k-form-build :value="formJsonData" ref="updateForm" :dynamicData="relationshipsData"/>
        <a-row type="flex" justify="center">
            <a-col span="3">
                <a-button @click="previousState()">{{$t('entity.action.cancel')}}</a-button>
            </a-col>
            <a-col span="3">
                <a-button type="primary" @click="save">{{$t('entity.action.save')}}</a-button>
            </a-col>
        </a-row>
    </a-card>-->
    <div>
        <component v-bind:is="updateFormTagName" :processEntityId="processEntityId" :hideSaveAndCancel="true" :methodsObject="methodsObject" :taskFormJsonData="formJsonData"></component>
        <a-row>
            <a-form layout="inline">
                <a-form-item label="Note">
                    <a-textarea placeholder="Basic usage" :rows="4" v-model="comment"/>
                </a-form-item>
            </a-form>
        </a-row>
        <a-row>
            <jhi-history-task-instance :processInstanceId="processInstanceId"></jhi-history-task-instance>
        </a-row>
    </div>
</template>
<script lang="ts" src="./flowable-task-update.component.ts">
</script>
<style>
    .ant-advanced-update-form .ant-form-item {
        display: flex;
    }
    .ant-advanced-update-form .ant-form-item-control-wrapper {
        flex: 1;
    }
</style>
