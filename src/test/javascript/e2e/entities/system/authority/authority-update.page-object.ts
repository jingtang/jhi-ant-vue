import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class AuthorityUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.systemAuthority.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#authority-name'));

  codeInput: ElementFinder = element(by.css('input#authority-code'));

  infoInput: ElementFinder = element(by.css('input#authority-info'));

  orderInput: ElementFinder = element(by.css('input#authority-order'));

  displayInput: ElementFinder = element(by.css('input#authority-display'));
  usersSelect = element(by.css('select#authority-users'));

  viewPermissionSelect = element(by.css('select#authority-viewPermission'));

  parentSelect = element(by.css('select#authority-parent'));
}
