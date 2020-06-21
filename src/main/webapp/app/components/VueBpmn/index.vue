<template>
    <a-card :bordered="false" class="fm2-container" style="margin-right: 0;">
        <div class="fm2-main el-container">
            <div class="center-container" direction="vertical">
                <a-row>
                    <a-col :md="18">
                        <a-row class="btn-bar" style="height: 45px;">
                            <a-col :md="18">
                                <slot name="action">
                                </slot>
                                <a-upload :show-upload-list="false" :before-upload="beforeUpload">
                                    <a-button> <a-icon type="upload" /> 导入 </a-button>
                                </a-upload>
                                <!--                                <a-button type="text" :size="'default'" icon="el-icon-upload2" @click="importBpmn">导入</a-button>-->
                                <a-button type="text" :size="'default'" icon="el-icon-download" @click="handleExportXmlAction">导出XML
                                </a-button>
                                <a-button type="text" :size="'default'" icon="el-icon-download" @click="handleExportSvgAction">导出SVG
                                </a-button>
                                <a-button type="text" :size="'default'" icon="el-icon-tickets" @click="xmlVisible = !xmlVisible">预览
                                </a-button>
                                <a-button type="text" :size="'default'" icon="el-icon-delete" @click="handleClear">清除</a-button>
                                <a-button type="text" :size="'default'" icon="el-icon-document" @click="save">保存</a-button>
                                <a-button type="text" :size="'default'" icon="el-icon-document" @click="handleSaveToServer">部署</a-button>
                            </a-col>
                            <a-col :md="6">
                                <slot name="commonTableSelect"></slot>
                            </a-col>
                        </a-row>
                        <a-row style="height: 70vh;border: dashed 1px;">
                            <div class="containers" :style="{height: getContainerHeight}">
                                <div class="canvas" ref="canvas"/>
                                <a-modal :visible.sync="xmlVisible" title="XML" :fullscreen="false" top="10vh">
                                    <vue-ace-editor v-model="process.xml"
                                                    @init="editorInit"
                                                    lang="xml"
                                                    theme="chrome"
                                                    width="100%"
                                                    height="400"
                                                    :options="{wrap: true, readOnly: true}">
                                    </vue-ace-editor>
                                    <span slot="footer" class="dialog-footer">
                                    <a-button icon="el-icon-document" v-clipboard:copy="process.xml"
                                              v-clipboard:success="onCopy">复 制</a-button>
                                    <a-button icon="el-icon-close" type="primary" @click="xmlVisible = false">关闭</a-button>
                                </span>
                                </a-modal>
                            </div>
                        </a-row>

                    </a-col>
                    <!--<a-col r :md="6">
                        <div id="js-properties-panel" ref="js-properties-panel" style="width: 100%;height: 600px;"></div>
                    </a-col>-->
                    <a-col :md="6">
                        <a-card class="widget-config-container" :tab-list="tabListNoTitle" :active-tab-key="noTitleKey" @tabChange="key => onTabChange(key, 'noTitleKey')">
                            <node-property-panel v-if="bpmnModeler && noTitleKey === 'node'"
                                                 :modeler="bpmnModeler"/>
                            <process-property-panel v-if="noTitleKey === 'process'"
                                                    :process-data="process"></process-property-panel>
                        </a-card>
                    </a-col>
                </a-row>
            </div>
        </div>
    </a-card>

</template>

<script>
    // bpmn-js 设计器
    import BpmnModeler from "bpmn-js/lib/Modeler";
    // import BpmnModeler from 'jeeplus-bpmn/lib/Modeler';
    // 对flowable的扩展
    import flowableExtensionModule from './jp-flowable-bpmn-moddle/lib';
    // import flowableModdle from "./jp-flowable-bpmn-moddle/resources/flowable";
    // import flowableModdleDescriptor from 'flowable-bpmn-moddle/resources/flowable';
    // import propertiesPanelModule from 'bpmn-js-properties-panel';
    // import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/bpmn';
    // import propertiesProviderModule from 'jp-bpmn-js-properties-panel/lib/provider/flowable';

    import NodePropertyPanel from "./NodePropertyPanel"; // 属性面板
    import ProcessPropertyPanel from "./ProcessPropertyPanel";

    import BpmData from "./BpmData";
    import VueAceEditor from 'vue2-ace-editor';

    export default {
        props: {
            processName: {
                type: String,
                default: '流程1567044459787',
            },
            processKey: {
                type: String,
                default: 'process1567044459787',
            },
            processDescription: {
                type: String,
                default: '描述',
            },
            bpmnData: {
                type: String,
                default: ''
            }
        },
        components: {
            NodePropertyPanel,
            ProcessPropertyPanel,
            VueAceEditor
        },
        data() {
            return {
                tabListNoTitle: [{key: 'node', tab: '节点'}, {key: 'process', tab: '流程'}],
                key: 'node',
                noTitleKey: 'node',
                bpmnModeler: null,
                process: {
                    name: this.processName,
                    id: this.processKey,
                    description: this.processDescription,
                    xml: '',
                    svg: ''
                },
                configTab: 'node',
                nodeProcessSelect: null,

                xmlVisible: false,
                element: null,
                bpmData: new BpmData(),
            };
        },
        methods: {
            /**
             * bind SVG element height.
             */
            getContainerHeight() {
                return (document.body.offsetHeight - 75) + 'px'
            },
            /**
             * init ace editor.
             */
            editorInit: function () {
                require('brace/ext/language_tools') //language extension prerequsite...
                require('brace/mode/xml')    //language
                require('brace/theme/chrome')
            },
            beforeUpload(file) {
                console.log(file);
                const reader = new FileReader();
                reader.readAsText(file);
                reader.onload = (event) => {
                    const data = event.target.result;
                    this.bpmnModeler.importXML(data, err => {
                        if (err) {
                            notification.error({
                                message: '提示',
                                description: '导入失败',
                            });
                        }
                    });
                };
                return false;
            },
            transformFilex(file) {
                console.log(file);
                return new Promise(resolve => {
                    const reader = new FileReader();
                    console.log(file);
                    reader.readAsText(file);
                    reader.onload = (event) => {
                        data = event.target.result;
                        this.bpmnModeler.importXML(data, err => {
                            if (err) {
                                notification.error({
                                    message: '提示',
                                    description: '导入失败',
                                });
                            }
                        });
                    };
                });
            },

            onTabChange(key, type) {
                this[type] = key;
            },
            /**
             * init
             */
            createNewDiagram(bpmnData) {
                // 初始化XML文本
                if (bpmnData && bpmnData.length > 0) {
                    this.process.xml = bpmnData;
                } else {
                    this.process.xml = `<?xml version="1.0" encoding="UTF-8"?>
<bpmn2:definitions xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
                   xmlns:dc="http://www.omg.org/spec/DD/20100524/DC"
                   targetNamespace="http://bpmn.io/schema/bpmn"
                   xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">
  <bpmn2:process id="${this.processKey}" name="${this.processName}">
    <bpmn2:documentation>${this.processDescription}</bpmn2:documentation>
    <bpmn2:startEvent id="StartEvent_01ydzqe" />
  </bpmn2:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="process1567044459787">
      <bpmndi:BPMNShape id="StartEvent_01ydzqe_di" bpmnElement="StartEvent_01ydzqe">
        <dc:Bounds x="242" y="212" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="247" y="263" width="25" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn2:definitions>`;
                }

                // 将字符串转换成图显示出来
                this.bpmnModeler.importXML(this.process.xml, err => {
                    if (err) {
                        console.error(err);
                    } else {
                        this.adjustPalette();
                    }
                });
            },

            // 调整左侧工具栏排版
            adjustPalette() {
                try {
                    // 获取 bpmn 设计器实例
                    const canvas = this.$refs.canvas;
                    const djsPalette = canvas.children[0].children[1].children[4];
                    const djsPalStyle = {
                        width: "130px",
                        padding: "5px",
                        background: "white",
                        left: "20px",
                        borderRadius: 0
                    };
                    for (var key in djsPalStyle) {
                        djsPalette.style[key] = djsPalStyle[key];
                    }
                    const palette = djsPalette.children[0];
                    const allGroups = palette.children;
                    allGroups[0].style["display"] = "none";
                    // 修改控件样式
                    for (var gKey in allGroups) {
                        const group = allGroups[gKey];
                        for (var cKey in group.children) {
                            const control = group.children[cKey];
                            const controlStyle = {
                                display: "flex",
                                justifyContent: "flex-start",
                                alignItems: "center",
                                width: "100%",
                                padding: "5px"
                            };
                            if (
                                control.className &&
                                control.dataset &&
                                control.className.indexOf("entry") !== -1
                            ) {
                                const controlProps = this.bpmData.getControl(
                                    control.dataset.action
                                );
                                control.innerHTML = `<div style='font-size: 14px;font-weight:500;margin-left:15px;'>${
                                    controlProps["title"]
                                }</div>`;
                                if (controlProps['tooltip']) {
                                    control.title = controlProps['tooltip'];
                                }
                                for (var csKey in controlStyle) {
                                    control.style[csKey] = controlStyle[csKey];
                                }
                            }
                        }
                    }
                } catch (e) {
                    console.log(e);
                }
            },

            // 当图发生改变的时候会调用这个函数，这个data就是图的xml
            setEncoded(type, data) {
                // 把xml转换为URI，下载要用到的
                const encodedData = encodeURIComponent(data);
                if (data) {
                    if (type === 'XML') {
                        // 获取到图的xml，保存就是把这个xml提交给后台
                        this.process.xml = data;
                        return {
                            filename: this.process.name + '.xml',
                            href: "data:application/bpmn20-xml;charset=UTF-8," + encodedData,
                            data: data
                        }
                    }
                    if (type === 'SVG') {
                        this.process.svg = data;
                        return {
                            filename: this.process.name + '.svg',
                            href: "data:application/text/xml;charset=UTF-8," + encodedData,
                            data: data
                        }
                    }
                }
            },
            /**
             * 导出BPMN XML文件
             */
            handleExportXmlAction() {
                const _this = this;
                this.bpmnModeler.saveXML({format: true}, function (err, xml) {
                    if (err) {
                        console.error(err);
                    }
                    let {filename, href} = _this.setEncoded('XML', xml);
                    if (href && filename) {
                        let a = document.createElement('a');
                        a.download = filename; //指定下载的文件名
                        a.href = href; //  URL对象
                        a.click(); // 模拟点击
                        URL.revokeObjectURL(a.href); // 释放URL 对象
                    }
                });
            },
            save() {
                const _this = this;
                this.bpmnModeler.saveXML({format: true}, function (err, xml) {
                    if (err) {
                        console.error(err);
                        return;
                    }
                    _this.$emit('save', xml);
                });
            },
            handleSaveToServer() {
                const _this = this;
                this.bpmnModeler.saveXML({format: true}, function (err, xml) {
                    if (err) {
                        console.error(err);
                    }
                    const filename = _this.process.name + '.bpmn';
                    const fd = new FormData();
                    fd.append('deployment-name',filename);
                    const blob = new Blob([xml]);
                    fd.append('data', blob, filename);
                    _this.$emit('deploymentToServer', fd);
                });
            },
            /**
             * 导出BPMN SVG文件
             */
            handleExportSvgAction() {
                const _this = this;
                this.bpmnModeler.saveSVG(function (err, svg) {
                    if (err) {
                        console.error(err);
                    }
                    let {filename, href} = _this.setEncoded('SVG', svg);
                    if (href && filename) {
                        let a = document.createElement('a');
                        a.download = filename;
                        a.href = href;
                        a.click();
                        URL.revokeObjectURL(a.href);
                    }
                });
            },
            /**
             * 清空设计器内容
             */
            handleClear() {
                this.createNewDiagram();
            },
            /**
             * 复制内容到剪切板成功回调
             */
            onCopy() {
                this.$message.success('内容复制成功');
            }
        },

        mounted() {
            const canvas = this.$refs.canvas;
            // 生成实例
            this.bpmnModeler = new BpmnModeler({
                container: canvas,
                // propertiesPanel: {
                //     parent: '#js-properties-panel'
                // },
                additionalModules: [
                    flowableExtensionModule,
                    // propertiesProviderModule,
                    // propertiesPanelModule
                ],
                moddleExtensions: {
                    flowable: flowableExtensionModule
                }
            });

            // 监听流程图改变事件
            const _this = this;
            this.bpmnModeler.on("commandStack.changed", () => {
                _this.bpmnModeler.saveSVG({format: true}, function (err, svg) {
                    _this.setEncoded('SVG', err ? null : svg);
                });
                _this.bpmnModeler.saveXML({format: true}, function (err, xml) {
                    _this.setEncoded('XML', err ? null : xml);
                });
            });

            // 新增流程定义
            this.createNewDiagram(this.bpmnData);
        },
        watch: {
            'bpmnData': {
                handler(val) {
                    this.process.xml = val;
                    this.bpmnModeler.importXML(this.process.xml, err => {
                        if (err) {
                            console.error(err);
                        } else {
                            // this.adjustPalette();
                            this.$message.success('流程数据已经更新。')
                        }
                    });
                }
            },
            'processName': {
                handler(val) {
                    this.process.name = val;
                }
            },
            'processKey': {
                handler(val) {
                    this.process.id = val;
                }
            }
        }
    };
</script>

<style lang="less">
    /*左边工具栏以及编辑节点的样式*/
    @import "bpmn-js/dist/assets/diagram-js.css";
    @import "bpmn-js/dist/assets/bpmn-font/css/bpmn.css";
    @import "bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css";
    @import "bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css";
    @import "~ant-design-vue/lib/style/index";
    /*@import "jp-bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css";*/
    .widget-config-container {
        padding: 0;
        .ant-card-body {
            padding: 0;
        }
    }
    .fm2-container {
        background: #fff;
        height: 80vh;
        border: 1px solid #e0e0e0;


        .el-container {
            height: 100% !important;
        }

        & > .el-container {
            background: #fff;
        }

        .fm2-main {
            position: relative;

            & > .el-container {
                position: absolute;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
            }
        }

        main {
            padding: 0;
        }

        footer {
            height: 30px;
            line-height: 30px;
            border-top: 1px solid #e0e0e0;
            font-size: 12px;
            text-align: right;
            color: @primary-color;
            background: #fafafa;

            a {
                color: @primary-color;
            }
        }
    }

    .containers {
        /*position: absolute;*/
        background-color: #ffffff;
        width: 100%;
        height: 100%;

        .canvas {
            width: 100%;
            height: 100%;
        }

        .panel {
            position: absolute;
            right: 0;
            top: 50px;
            width: 300px;
        }

        .bjs-powered-by {
            display: none;
        }

        .toolbar {
            position: absolute;
            top: 0;
            right: 320px;
            height: 40px;
            width: 600px;
            border: 1px solid red;

            a {
                text-decoration: none;
                margin: 5px;
                color: #409eff;
            }
        }
    }
</style>
