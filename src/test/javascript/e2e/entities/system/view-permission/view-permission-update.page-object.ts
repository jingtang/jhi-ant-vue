import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class ViewPermissionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.systemViewPermission.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  textInput: ElementFinder = element(by.css('input#view-permission-text'));

  i18nInput: ElementFinder = element(by.css('input#view-permission-i18n'));

  groupInput: ElementFinder = element(by.css('input#view-permission-group'));

  linkInput: ElementFinder = element(by.css('input#view-permission-link'));

  externalLinkInput: ElementFinder = element(by.css('input#view-permission-externalLink'));

  targetSelect = element(by.css('select#view-permission-target'));

  iconInput: ElementFinder = element(by.css('input#view-permission-icon'));

  disabledInput: ElementFinder = element(by.css('input#view-permission-disabled'));

  hideInput: ElementFinder = element(by.css('input#view-permission-hide'));

  hideInBreadcrumbInput: ElementFinder = element(by.css('input#view-permission-hideInBreadcrumb'));

  shortcutInput: ElementFinder = element(by.css('input#view-permission-shortcut'));

  shortcutRootInput: ElementFinder = element(by.css('input#view-permission-shortcutRoot'));

  reuseInput: ElementFinder = element(by.css('input#view-permission-reuse'));

  codeInput: ElementFinder = element(by.css('input#view-permission-code'));

  descriptionInput: ElementFinder = element(by.css('input#view-permission-description'));

  typeSelect = element(by.css('select#view-permission-type'));

  orderInput: ElementFinder = element(by.css('input#view-permission-order'));

  apiPermissionCodesInput: ElementFinder = element(by.css('input#view-permission-apiPermissionCodes'));

  apiPermissionsSelect = element(by.css('select#view-permission-apiPermissions'));

  parentSelect = element(by.css('select#view-permission-parent'));
}
