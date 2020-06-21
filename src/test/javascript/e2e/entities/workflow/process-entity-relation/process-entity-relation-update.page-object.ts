import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../../page-objects/alert-page';

export default class ProcessEntityRelationUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('jhiAntVueApp.workflowProcessEntityRelation.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  entityTypeInput: ElementFinder = element(by.css('input#process-entity-relation-entityType'));

  entityIdInput: ElementFinder = element(by.css('input#process-entity-relation-entityId'));

  processInstanceIdInput: ElementFinder = element(by.css('input#process-entity-relation-processInstanceId'));

  statusSelect = element(by.css('select#process-entity-relation-status'));
}
