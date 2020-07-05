import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CommonQueryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.commonQueryCommonQuery.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#common-query-name'));

  descriptionInput: ElementFinder = element(by.css('input#common-query-description'));

  lastModifiedTimeInput: ElementFinder = element(by.css('input#common-query-lastModifiedTime'));

  modifierSelect = element(by.css('select#common-query-modifier'));

  commonTableSelect = element(by.css('select#common-query-commonTable'));
}
