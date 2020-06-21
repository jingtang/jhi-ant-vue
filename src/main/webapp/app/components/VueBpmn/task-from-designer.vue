<template>
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
        <k-form-design :showToolbarsText="true" :showHead="false" :toolbars="['save', 'preview', 'exportJson']"
                       title="表单设计" @save="saveToServer" ref='designer' toolbarsTop>
            <template slot="left-action">
                <a-tooltip title="从原始数据结构重新生成">
                    <a @click="rebuildFromServer">
                        <a-icon type="cloud-download" />
                        <span>原始生成</span>
                    </a>
                </a-tooltip>
            </template>
        </k-form-design>
    </a-card>
</template>

<script lang="ts" src="./task-form-designer.component.ts">
</script>
