<template>
  <a-row>
      <a-select @click.native="showModal($event)" :open="false" :disabled="props.disabled"
                v-model="value" :mode="props.mode" style="width: 100%">
          <a-icon slot="suffixIcon" type="profile" />
          <a-select-option v-for="option in (options)"
                           :value="option[optionProps.value]"
                           :key="option[optionProps.value]"
                           :disabled="option[optionProps.disabled]">
              {{option[optionProps.label]}}
          </a-select-option>
      </a-select>
      <a-modal
          :destroyOnClose="true"
          :footer="null"
          title="请选择"
          :visible="modalVisible"
          :maskClosable="false"
          @cancel="cancelHideModal"
          @ok="okHideModal"
      >
          <component v-bind:is="selectListName" :commonTableName="''" @ok="okHideModal" @cancel="cancelHideModal"></component>
<!--          <slot name="listTable" @cancel="hideModal" @ok="hideModal"></slot>-->
      </a-modal>
  </a-row>
</template>

<script>
    export default {
        name: "select-list-modal",
        model: {
            prop: 'value',
            event: 'change'
        },
        data() {
            return {
                modalVisible: false,
                resultValue: null
            }
        },
        props: {
            selectListName: {
                type: String,
                default: 'jhi-common-table-compact'
            },
            props: {
                type: Object,
                default: () => { return {};}
            },
            optionProps: {
                type: Object,
                default: () => { return {value: 'value', label: 'lable', disabled: 'disabled'};}
            },
            options: {
                type: Array,
                default: () => { return []}
            },
            value: {
                type: String | Number,
                default: null
            }
        },
        methods: {
            showModal(e) {
                e.stopPropagation();
                e.preventDefault();
                this.modalVisible = true;
            },
            okHideModal(selectRecords) {
                if (this.props.mode !== 'multiple') {
                    selectRecords = selectRecords.slice(0,1);
                }
                if ( selectRecords && selectRecords.length > 0 ) {
                    selectRecords.forEach( record => {
                        const exitValue = this.options.some( option => option[this.optionProps.value] === record[this.optionProps.value])
                        if (!exitValue) {
                            const newOption = {};
                            newOption[this.optionProps.value] = record[this.optionProps.value];
                            newOption[this.optionProps.label] = record[this.optionProps.label];
                            this.options.push(newOption);
                        }
                        if (this.props.mode === 'multiple') {
                            this.resultValue = [];
                            this.resultValue.push(record[this.optionProps.value]);
                        } else {
                            this.resultValue = record[this.optionProps.value];
                        }
                    });
                }
                this.$emit('change',this.resultValue);
                this.modalVisible = false;
            },
            cancelHideModal(e) {
                this.modalVisible = false;
            }
        }
    }
</script>
