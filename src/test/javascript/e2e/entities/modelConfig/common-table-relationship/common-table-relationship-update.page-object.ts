import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CommonTableRelationshipUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.modelConfigCommonTableRelationship.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#common-table-relationship-name'));

  relationshipTypeSelect = element(by.css('select#common-table-relationship-relationshipType'));

  sourceTypeSelect = element(by.css('select#common-table-relationship-sourceType'));

  otherEntityFieldInput: ElementFinder = element(by.css('input#common-table-relationship-otherEntityField'));

  otherEntityNameInput: ElementFinder = element(by.css('input#common-table-relationship-otherEntityName'));

  relationshipNameInput: ElementFinder = element(by.css('input#common-table-relationship-relationshipName'));

  otherEntityRelationshipNameInput: ElementFinder = element(by.css('input#common-table-relationship-otherEntityRelationshipName'));

  columnWidthInput: ElementFinder = element(by.css('input#common-table-relationship-columnWidth'));

  orderInput: ElementFinder = element(by.css('input#common-table-relationship-order'));

  fixedSelect = element(by.css('select#common-table-relationship-fixed'));

  editInListInput: ElementFinder = element(by.css('input#common-table-relationship-editInList'));

  enableFilterInput: ElementFinder = element(by.css('input#common-table-relationship-enableFilter'));

  hideInListInput: ElementFinder = element(by.css('input#common-table-relationship-hideInList'));

  hideInFormInput: ElementFinder = element(by.css('input#common-table-relationship-hideInForm'));

  fontColorInput: ElementFinder = element(by.css('input#common-table-relationship-fontColor'));

  backgroundColorInput: ElementFinder = element(by.css('input#common-table-relationship-backgroundColor'));

  helpInput: ElementFinder = element(by.css('input#common-table-relationship-help'));

  ownerSideInput: ElementFinder = element(by.css('input#common-table-relationship-ownerSide'));

  dataNameInput: ElementFinder = element(by.css('input#common-table-relationship-dataName'));

  webComponentTypeInput: ElementFinder = element(by.css('input#common-table-relationship-webComponentType'));

  otherEntityIsTreeInput: ElementFinder = element(by.css('input#common-table-relationship-otherEntityIsTree'));

  showInFilterTreeInput: ElementFinder = element(by.css('input#common-table-relationship-showInFilterTree'));

  dataDictionaryCodeInput: ElementFinder = element(by.css('input#common-table-relationship-dataDictionaryCode'));

  clientReadOnlyInput: ElementFinder = element(by.css('input#common-table-relationship-clientReadOnly'));
  relationEntitySelect = element(by.css('select#common-table-relationship-relationEntity'));

  dataDictionaryNodeSelect = element(by.css('select#common-table-relationship-dataDictionaryNode'));

  commonTableSelect = element(by.css('select#common-table-relationship-commonTable'));
}
