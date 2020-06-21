import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class ProcessTableConfigUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.workflowProcessTableConfig.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  processDefinitionIdInput: ElementFinder = element(by.css('input#process-table-config-processDefinitionId'));

  processDefinitionKeyInput: ElementFinder = element(by.css('input#process-table-config-processDefinitionKey'));

  processDefinitionNameInput: ElementFinder = element(by.css('input#process-table-config-processDefinitionName'));

  descriptionInput: ElementFinder = element(by.css('input#process-table-config-description'));

  processBpmnDataInput: ElementFinder = element(by.css('textarea#process-table-config-processBpmnData'));

  deploiedInput: ElementFinder = element(by.css('input#process-table-config-deploied'));
  commonTableSelect = element(by.css('select#process-table-config-commonTable'));

  creatorSelect = element(by.css('select#process-table-config-creator'));
}
