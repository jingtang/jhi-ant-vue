import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class BusinessTypeUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.companyBusinessType.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#business-type-name'));

  codeInput: ElementFinder = element(by.css('input#business-type-code'));

  descriptionInput: ElementFinder = element(by.css('input#business-type-description'));

  iconInput: ElementFinder = element(by.css('input#business-type-icon'));
}
