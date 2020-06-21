import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class DataDictionaryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.settingsDataDictionary.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#data-dictionary-name'));

  codeInput: ElementFinder = element(by.css('input#data-dictionary-code'));

  descriptionInput: ElementFinder = element(by.css('input#data-dictionary-description'));

  fontColorInput: ElementFinder = element(by.css('input#data-dictionary-fontColor'));

  backgroundColorInput: ElementFinder = element(by.css('input#data-dictionary-backgroundColor'));

  parentSelect = element(by.css('select#data-dictionary-parent'));
}
