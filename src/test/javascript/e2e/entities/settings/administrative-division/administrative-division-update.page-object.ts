import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class AdministrativeDivisionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.settingsAdministrativeDivision.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#administrative-division-name'));

  areaCodeInput: ElementFinder = element(by.css('input#administrative-division-areaCode'));

  cityCodeInput: ElementFinder = element(by.css('input#administrative-division-cityCode'));

  mergerNameInput: ElementFinder = element(by.css('input#administrative-division-mergerName'));

  shortNameInput: ElementFinder = element(by.css('input#administrative-division-shortName'));

  zipCodeInput: ElementFinder = element(by.css('input#administrative-division-zipCode'));

  levelInput: ElementFinder = element(by.css('input#administrative-division-level'));

  lngInput: ElementFinder = element(by.css('input#administrative-division-lng'));

  latInput: ElementFinder = element(by.css('input#administrative-division-lat'));

  parentSelect = element(by.css('select#administrative-division-parent'));
}
