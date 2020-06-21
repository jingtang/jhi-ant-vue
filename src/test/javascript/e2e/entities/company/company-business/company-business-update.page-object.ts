import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class CompanyBusinessUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.companyCompanyBusiness.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  statusSelect = element(by.css('select#company-business-status'));

  expirationTimeInput: ElementFinder = element(by.css('input#company-business-expirationTime'));

  startTimeInput: ElementFinder = element(by.css('input#company-business-startTime'));

  operateUserIdInput: ElementFinder = element(by.css('input#company-business-operateUserId'));

  groupIdInput: ElementFinder = element(by.css('input#company-business-groupId'));

  businessTypeSelect = element(by.css('select#company-business-businessType'));

  companySelect = element(by.css('select#company-business-company'));
}
