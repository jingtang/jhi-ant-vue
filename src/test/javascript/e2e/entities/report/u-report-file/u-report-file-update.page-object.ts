import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class UReportFileUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.reportUReportFile.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#u-report-file-name'));

  contentInput: ElementFinder = element(by.css('textarea#u-report-file-content'));

  createAtInput: ElementFinder = element(by.css('input#u-report-file-createAt'));

  updateAtInput: ElementFinder = element(by.css('input#u-report-file-updateAt'));

  commonTableSelect = element(by.css('select#u-report-file-commonTable'));
}
