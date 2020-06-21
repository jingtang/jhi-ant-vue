import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class LeaveUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.workflowLeave.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  createTimeInput: ElementFinder = element(by.css('input#leave-createTime'));

  nameInput: ElementFinder = element(by.css('input#leave-name'));

  daysInput: ElementFinder = element(by.css('input#leave-days'));

  startTimeInput: ElementFinder = element(by.css('input#leave-startTime'));

  endTimeInput: ElementFinder = element(by.css('input#leave-endTime'));

  reasonInput: ElementFinder = element(by.css('input#leave-reason'));

  creatorSelect = element(by.css('select#leave-creator'));
}
