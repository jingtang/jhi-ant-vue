<template>
    <div class="property-panel" ref="propertyPanel">
        <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol" :model="form">
            <a-form-item label="类型" style="margin-bottom: 5px;">
                <a-input v-model="form.$type" :disabled="true"></a-input>
            </a-form-item>
            <a-form-item label="ID" style="margin-bottom: 5px;">
                <a-input v-model="form.id" :disabled="true"></a-input>
            </a-form-item>
            <a-form-item label="名称" style="margin-bottom: 5px;">
                <a-input v-model="form.name" @change="nameChange" :disabled="!this.form.$type"></a-input>
            </a-form-item>
            <!--<a-form-item label="节点颜色">
              <el-color-picker v-model="form.color" @active-change="colorChange"></el-color-picker>
            </a-form-item>-->

            <template v-if="element && userTask">
                <!-- 指定节点表单 -->
                <a-form-item label="表单类型" style="margin-bottom: 5px;">
                    <a-input v-model="form.formCategory"></a-input>
                </a-form-item>
                <a-form-item label="表单KEY" style="margin-bottom: 5px;">
                    <a-input v-model="form.formKey">
                        <a-icon slot="addonAfter" type="setting" @click="designForm"/>
                    </a-input>
                </a-form-item>
                <!-- 任务节点允许选择人员 -->
                <a-form-item label="节点人员" style="margin-bottom: 5px;">
                    <a-select v-model="form.userType" placeholder="请选择" @change="typeChange">
                        <a-select-option value="assignee">指定人员</a-select-option>
                        <a-select-option value="candidateUsers">候选人员</a-select-option>
                        <a-select-option value="candidateGroups">角色/岗位</a-select-option>
                    </a-select>
                </a-form-item>
                <!-- 指定人员 -->
                <a-form-item label="指定人员" v-if="userTask && form.userType === 'assignee'" style="margin-bottom: 5px;">
                    <select-list-modal v-model="form.assignee"
                                       :option-props="candidateUsersModeProp"
                                       :select-list-name="'jhi-user-compact'"
                                       @change="(value) => addUser({assignee: value})">
                    </select-list-modal>
                    <!--<a-select
                            v-model="form.assignee"
                            placeholder="请选择"
                            key="1"
                            @change="(value) => addUser({assignee: value})"
                    >
                      <a-select-option
                              v-for="item in users"
                              :key="item.value"
                              :value="item.value"
                      >{{item.label}}</a-select-option>
                    </a-select>-->
                </a-form-item>
                <!-- 候选人员 -->
                <a-form-item label="候选人员" v-else-if="userTask && form.userType === 'candidateUsers'"
                             style="margin-bottom: 5px;">
                    <select-list-modal v-model="form.candidateUsers"
                                       :option-props="{value: 'login', label: 'firstName'}"
                                       :select-list-name="'jhi-user-compact'"
                                       @change="(value) => addUser({candidateUsers: value.join(',') || value})">
                    </select-list-modal>
                    <!--<a-select
                            v-model="form.candidateUsers"
                            placeholder="请选择"
                            key="2"
                            mode="multiple"
                            @change="(value) => addUser({candidateUsers: value.join(',') || value})"
                    >
                      <a-select-option
                              v-for="item in users"
                              :key="item.value"
                              :value="item.value"
                      >{{item.label}}</a-select-option>
                    </a-select>-->
                </a-form-item>
                <!-- 角色/岗位 -->
                <a-form-item label="角色/岗位" v-else-if="userTask && form.userType === 'candidateGroups'"
                             style="margin-bottom: 5px;">
                    <select-list-modal v-model="form.candidateGroups"
                                       :option-props="{value: 'id', label: 'name'}"
                                       :select-list-name="'jhi-company-customer-compact'"
                                       @change="(value) => addUser({candidateGroups: value})">
                    </select-list-modal>
                    <!--<a-select
                            v-model="form.candidateGroups"
                            placeholder="请选择"
                            @change="(value) => addUser({candidateGroups: value})"
                    >
                      <a-select-option
                              v-for="item in roles"
                              :key="item.value"
                              :value="item.value"
                      >{{item.label}}</a-select-option>
                    </a-select>-->
                </a-form-item>
            </template>

            <!-- 分支允许添加条件 -->
            <a-form-item label="分支条件" v-if="sequenceFlow" style="margin-bottom: 5px;">
                <a-select v-model="form.user" placeholder="请选择">
                    <a-select-option
                        v-for="item in users"
                        :key="item.value"
                        :value="item.value"
                    >{{item.label}}
                    </a-select-option>
                </a-select>
            </a-form-item>
        </a-form-model>
        <a-modal
            title="设计表单"
            style="top: 0;bottom: 0;height: 100%;"
            :bodyStyle="{padding: 0}"
            :visible="showDesinger"
            :destroyOnClose="true"
            :width="'100%'"
            :maskClosable="false"
            :footer="null"
            @cancel="()=> {this.showDesinger = false;}"
        >
            <task-form-design :nodeId="form.id"></task-form-design>
        </a-modal>
    </div>
</template>

<script>
    import TaskFormDesignComponent from '@/components/VueBpmn/task-from-designer';
    export default {
        name: "NodePropertyPanel",
        components: {
            'task-form-design': TaskFormDesignComponent
        },
        props: {
            /**
             * BpmnModeler设计器对象
             */
            modeler: {
                type: Object,
                required: true
            },

        },
        computed: {
            userTask() {
                if (!this.element) {
                    return;
                }
                return this.element.type === "bpmn:UserTask";
            },
            sequenceFlow() {
                if (!this.element) {
                    return;
                }
                return this.element.type === "bpmn:SequenceFlow";
            }
        },
        data() {
            return {
                candidateUsersModeProp: {value: 'login', label: 'firstName'},
                candidateUsersMode: {
                    mode: 'multiple'
                },
                labelCol: {span: 8},
                wrapperCol: {span: 16},
                showDesinger: false,
                form: {
                    id: "",
                    name: "",
                    color: null
                },
                element: {},
                users: [
                    {
                        value: "zhangsan",
                        label: "张三"
                    },
                    {
                        value: "lisi",
                        label: "李四"
                    },
                    {
                        value: "wangwu",
                        label: "王五"
                    }
                ],
                roles: [
                    {
                        value: "manager",
                        label: "经理"
                    },
                    {
                        value: "personnel",
                        label: "人事"
                    },
                    {
                        value: "charge",
                        label: "主管"
                    }
                ]
            };
        },
        mounted() {
            this.handleModeler();
        },
        methods: {
            designForm() {
                this.showDesinger = true;
            },
            handleModeler() {
                // 监听节点选择变化
                this.modeler.on("selection.changed", e => {
                    const element = e.newSelection[0];
                    console.log('element', element);
                    if (!element) {
                        return;
                    }
                    this.element = element;

                    this.form = {
                        ...element.businessObject,
                        ...element.businessObject.$attrs
                    };
                    console.log(this.form);
                    if (this.form['assignee']) {
                        this.form.userType = 'assignee';
                    } else if (this.form['candidateUsers']) {
                        this.form.userType = 'candidateUsers';
                    } else if (this.form['candidateGroups']) {
                        this.form.userType = 'candidateGroups';
                    }
                    if (this.form.userType === "candidateUsers") {
                        this.form["candidateUsers"] = this.form["candidateUsers"].split(",") || [];
                    }
                    this.$emit('tabChange', 'node');
                });

                //  监听节点属性变化
                this.modeler.on("element.changed", e => {
                    const {element} = e;
                    if (!element) {
                        return;
                    }
                    //  新增节点需要更新回属性面板
                    if (element.id === this.form.id) {
                        this.form.name = element.businessObject.name;
                        this.form = {...this.form};
                    }
                });
            },

            // 属性面板名称，更新回流程节点
            nameChange(value) {
                const modeling = this.modeler.get("modeling");
                modeling.updateLabel(this.element, this.form.name);
            },

            // 属性面板颜色，更新回流程节点
            colorChange(color) {
                const modeling = this.modeler.get("modeling");
                modeling.setColor(this.element, {
                    fill: null,
                    stroke: color
                });
                modeling.updateProperties(this.element, {color: color});
            },

            // 任务节点配置人员
            addUser(properties) {
                console.log(properties);
                delete properties['userType'];
                this.updateProperties(
                    /*Object.assign(properties, {
                        userType: Object.keys(properties)[0]
                    })*/
                    properties
                );
            },

            // 切换人员类型
            typeChange() {
                const types = ["assignee", "candidateUsers", "candidateGroups"];
                types.forEach(type => {
                    delete this.element.businessObject.$attrs[type];
                    delete this.form[type];
                });
            },

            // 在这里我们封装一个通用的更新节点属性的方法
            updateProperties(properties) {
                const modeling = this.modeler.get("modeling");
                console.log('updateProperties', properties);
                modeling.updateProperties(this.element, properties);
            }
        },

        watch: {
            'form.name': {
                handler(val) {
                    if (this.element) {
                        // this.element.businessObject.name = val;
                        console.log('after', this.element.businessObject);
                    }
                }
            }
        }
    };
</script>

<style lang="scss" scoped>
    .property-panel {
        padding: 10px;
        width: 100%;
    }
</style>
