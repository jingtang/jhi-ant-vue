import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class GpsInfoUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.settingsGpsInfo.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  typeSelect = element(by.css('select#gps-info-type'));

  latitudeInput: ElementFinder = element(by.css('input#gps-info-latitude'));

  longitudeInput: ElementFinder = element(by.css('input#gps-info-longitude'));

  addressInput: ElementFinder = element(by.css('input#gps-info-address'));
}
