<template>
    <vue-bpmn @deploymentToServer="deployment" @save="saveToServer" :bpmnData="processDefinitionBpmn" :processKey="processDefinitionKey" :processName="processDefinitionName">
        <template v-slot:commonTableSelect>
            <select-list-modal :options="commonTables" :optionProps="{value: 'id', label: 'name'}" v-model="commonTableId">
            </select-list-modal>
        </template>
    </vue-bpmn>
</template>

<script lang="ts" src="./workflow-design.component.ts">
</script>
