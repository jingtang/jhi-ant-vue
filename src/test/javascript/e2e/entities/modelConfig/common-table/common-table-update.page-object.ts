import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CommonTableUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.modelConfigCommonTable.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#common-table-name'));

  entityNameInput: ElementFinder = element(by.css('input#common-table-entityName'));

  tableNameInput: ElementFinder = element(by.css('input#common-table-tableName'));

  systemInput: ElementFinder = element(by.css('input#common-table-system'));

  clazzNameInput: ElementFinder = element(by.css('input#common-table-clazzName'));

  generatedInput: ElementFinder = element(by.css('input#common-table-generated'));

  creatAtInput: ElementFinder = element(by.css('input#common-table-creatAt'));

  generateAtInput: ElementFinder = element(by.css('input#common-table-generateAt'));

  generateClassAtInput: ElementFinder = element(by.css('input#common-table-generateClassAt'));

  descriptionInput: ElementFinder = element(by.css('input#common-table-description'));

  treeTableInput: ElementFinder = element(by.css('input#common-table-treeTable'));

  baseTableIdInput: ElementFinder = element(by.css('input#common-table-baseTableId'));

  recordActionWidthInput: ElementFinder = element(by.css('input#common-table-recordActionWidth'));

  listConfigInput: ElementFinder = element(by.css('textarea#common-table-listConfig'));

  formConfigInput: ElementFinder = element(by.css('textarea#common-table-formConfig'));

  creatorSelect = element(by.css('select#common-table-creator'));

  businessTypeSelect = element(by.css('select#common-table-businessType'));
}
