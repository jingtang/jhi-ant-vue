import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class ApiPermissionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.systemApiPermission.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#api-permission-name'));

  codeInput: ElementFinder = element(by.css('input#api-permission-code'));

  descriptionInput: ElementFinder = element(by.css('input#api-permission-description'));

  typeSelect = element(by.css('select#api-permission-type'));

  methodInput: ElementFinder = element(by.css('input#api-permission-method'));

  urlInput: ElementFinder = element(by.css('input#api-permission-url'));

  parentSelect = element(by.css('select#api-permission-parent'));
}
