import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class ProcessFormConfigUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.workflowProcessFormConfig.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  processDefinitionKeyInput: ElementFinder = element(by.css('input#process-form-config-processDefinitionKey'));

  taskNodeIdInput: ElementFinder = element(by.css('input#process-form-config-taskNodeId'));

  taskNodeNameInput: ElementFinder = element(by.css('input#process-form-config-taskNodeName'));

  commonTableIdInput: ElementFinder = element(by.css('input#process-form-config-commonTableId'));

  formDataInput: ElementFinder = element(by.css('textarea#process-form-config-formData'));
}
