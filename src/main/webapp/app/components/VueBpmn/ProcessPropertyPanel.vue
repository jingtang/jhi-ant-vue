<template>
  <div class="property-panel" ref="processPropertyPanel">
    <a-form :layout="'horizontal'" :model="processData"
            :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="流程名称" style="margin-bottom: 5px;">
        <a-input v-model="processData.name" disabled></a-input>
      </a-form-item>
      <a-form-item label="流程Key" style="margin-bottom: 5px;">
        <a-input v-model="processData.id" disabled></a-input>
      </a-form-item>
      <a-form-item label="流程描述" style="margin-bottom: 5px;">
        <a-input v-model="processData.description" disabled></a-input>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  export default {
    name: "ProcessPropertyPanel",
    props: {
      processData: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
          labelCol: { span: 8 },
          wrapperCol: { span: 16 },
        process: {
          name: this.processData.name,
          id: '',
          description: ''
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .property-panel {
    padding: 10px;
    width: 100%;
  }
</style>
