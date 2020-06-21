import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CompanyUserUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.companyCompanyUser.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  userSelect = element(by.css('select#company-user-user'));

  companySelect = element(by.css('select#company-user-company'));
}
