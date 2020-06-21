import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CommonTableFieldUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.modelConfigCommonTableField.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  titleInput: ElementFinder = element(by.css('input#common-table-field-title'));

  entityFieldNameInput: ElementFinder = element(by.css('input#common-table-field-entityFieldName'));

  typeSelect = element(by.css('select#common-table-field-type'));

  tableColumnNameInput: ElementFinder = element(by.css('input#common-table-field-tableColumnName'));

  columnWidthInput: ElementFinder = element(by.css('input#common-table-field-columnWidth'));

  orderInput: ElementFinder = element(by.css('input#common-table-field-order'));

  editInListInput: ElementFinder = element(by.css('input#common-table-field-editInList'));

  hideInListInput: ElementFinder = element(by.css('input#common-table-field-hideInList'));

  hideInFormInput: ElementFinder = element(by.css('input#common-table-field-hideInForm'));

  enableFilterInput: ElementFinder = element(by.css('input#common-table-field-enableFilter'));

  validateRulesInput: ElementFinder = element(by.css('input#common-table-field-validateRules'));

  showInFilterTreeInput: ElementFinder = element(by.css('input#common-table-field-showInFilterTree'));

  fixedSelect = element(by.css('select#common-table-field-fixed'));

  sortableInput: ElementFinder = element(by.css('input#common-table-field-sortable'));

  treeIndicatorInput: ElementFinder = element(by.css('input#common-table-field-treeIndicator'));

  clientReadOnlyInput: ElementFinder = element(by.css('input#common-table-field-clientReadOnly'));

  fieldValuesInput: ElementFinder = element(by.css('input#common-table-field-fieldValues'));

  notNullInput: ElementFinder = element(by.css('input#common-table-field-notNull'));

  systemInput: ElementFinder = element(by.css('input#common-table-field-system'));

  helpInput: ElementFinder = element(by.css('input#common-table-field-help'));

  fontColorInput: ElementFinder = element(by.css('input#common-table-field-fontColor'));

  backgroundColorInput: ElementFinder = element(by.css('input#common-table-field-backgroundColor'));

  commonTableSelect = element(by.css('select#common-table-field-commonTable'));
}
