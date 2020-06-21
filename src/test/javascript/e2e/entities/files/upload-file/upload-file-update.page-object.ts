import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class UploadFileUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.filesUploadFile.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  fullNameInput: ElementFinder = element(by.css('input#upload-file-fullName'));

  nameInput: ElementFinder = element(by.css('input#upload-file-name'));

  extInput: ElementFinder = element(by.css('input#upload-file-ext'));

  typeInput: ElementFinder = element(by.css('input#upload-file-type'));

  urlInput: ElementFinder = element(by.css('input#upload-file-url'));

  pathInput: ElementFinder = element(by.css('input#upload-file-path'));

  folderInput: ElementFinder = element(by.css('input#upload-file-folder'));

  entityNameInput: ElementFinder = element(by.css('input#upload-file-entityName'));

  createAtInput: ElementFinder = element(by.css('input#upload-file-createAt'));

  fileSizeInput: ElementFinder = element(by.css('input#upload-file-fileSize'));

  referenceCountInput: ElementFinder = element(by.css('input#upload-file-referenceCount'));

  userSelect = element(by.css('select#upload-file-user'));
}
