import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CompanyCustomerUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.companyCompanyCustomer.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#company-customer-name'));

  codeInput: ElementFinder = element(by.css('input#company-customer-code'));

  addressInput: ElementFinder = element(by.css('input#company-customer-address'));

  phoneNumInput: ElementFinder = element(by.css('input#company-customer-phoneNum'));

  logoInput: ElementFinder = element(by.css('input#company-customer-logo'));

  contactInput: ElementFinder = element(by.css('input#company-customer-contact'));

  createUserIdInput: ElementFinder = element(by.css('input#company-customer-createUserId'));

  createTimeInput: ElementFinder = element(by.css('input#company-customer-createTime'));

  parentSelect = element(by.css('select#company-customer-parent'));
}
