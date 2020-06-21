import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class UploadImageUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.filesUploadImage.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  fullNameInput: ElementFinder = element(by.css('input#upload-image-fullName'));

  nameInput: ElementFinder = element(by.css('input#upload-image-name'));

  extInput: ElementFinder = element(by.css('input#upload-image-ext'));

  typeInput: ElementFinder = element(by.css('input#upload-image-type'));

  urlInput: ElementFinder = element(by.css('input#upload-image-url'));

  pathInput: ElementFinder = element(by.css('input#upload-image-path'));

  folderInput: ElementFinder = element(by.css('input#upload-image-folder'));

  entityNameInput: ElementFinder = element(by.css('input#upload-image-entityName'));

  createAtInput: ElementFinder = element(by.css('input#upload-image-createAt'));

  fileSizeInput: ElementFinder = element(by.css('input#upload-image-fileSize'));

  smartUrlInput: ElementFinder = element(by.css('input#upload-image-smartUrl'));

  mediumUrlInput: ElementFinder = element(by.css('input#upload-image-mediumUrl'));

  referenceCountInput: ElementFinder = element(by.css('input#upload-image-referenceCount'));

  userSelect = element(by.css('select#upload-image-user'));
}
