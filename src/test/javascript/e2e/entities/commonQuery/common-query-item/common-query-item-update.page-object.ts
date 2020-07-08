import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CommonQueryItemUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.commonQueryCommonQueryItem.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  prefixInput: ElementFinder = element(by.css('input#common-query-item-prefix'));

  fieldNameInput: ElementFinder = element(by.css('input#common-query-item-fieldName'));

  fieldTypeInput: ElementFinder = element(by.css('input#common-query-item-fieldType'));

  operatorInput: ElementFinder = element(by.css('input#common-query-item-operator'));

  valueInput: ElementFinder = element(by.css('input#common-query-item-value'));

  suffixInput: ElementFinder = element(by.css('input#common-query-item-suffix'));

  orderInput: ElementFinder = element(by.css('input#common-query-item-order'));

  querySelect = element(by.css('select#common-query-item-query'));
}
