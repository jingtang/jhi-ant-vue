/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import ProcessFormConfigComponentsPage, { ProcessFormConfigDeleteDialog } from './process-form-config.page-object';
import ProcessFormConfigUpdatePage from './process-form-config-update.page-object';
import ProcessFormConfigDetailsPage from './process-form-config-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden
} from '../../../util/utils';

const expect = chai.expect;

describe('ProcessFormConfig e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ProcessFormConfigUpdatePage;
  let detailsPage: ProcessFormConfigDetailsPage;
  let listPage: ProcessFormConfigComponentsPage;
  let deleteDialog: ProcessFormConfigDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load ProcessFormConfigs', async () => {
    await navBarPage.getEntityPage('process-form-config');
    listPage = new ProcessFormConfigComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create ProcessFormConfig page', async () => {
      await listPage.createButton.click();
      updatePage = new ProcessFormConfigUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.workflowProcessFormConfig.home.createOrEditLabel/);
    });

    it('should create and save ProcessFormConfigs', async () => {
      await updatePage.processDefinitionKeyInput.sendKeys('processDefinitionKey');
      expect(await updatePage.processDefinitionKeyInput.getAttribute('value')).to.match(/processDefinitionKey/);

      await updatePage.taskNodeIdInput.sendKeys('taskNodeId');
      expect(await updatePage.taskNodeIdInput.getAttribute('value')).to.match(/taskNodeId/);

      await updatePage.taskNodeNameInput.sendKeys('taskNodeName');
      expect(await updatePage.taskNodeNameInput.getAttribute('value')).to.match(/taskNodeName/);

      await updatePage.commonTableIdInput.sendKeys('5');
      expect(await updatePage.commonTableIdInput.getAttribute('value')).to.eq('5');

      await waitUntilDisplayed(updatePage.formDataInput);
      await updatePage.formDataInput.sendKeys('formData');

      expect(await updatePage.formDataInput.getAttribute('value')).to.match(/formData/);

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilDisplayed(listPage.successAlert);
      expect(await listPage.successAlert.isDisplayed()).to.be.true;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new ProcessFormConfigDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.workflowProcessFormConfig.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details ProcessFormConfig page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ProcessFormConfigDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit ProcessFormConfig page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.processDefinitionKeyInput.clear();
        await updatePage.processDefinitionKeyInput.sendKeys('modified');
        expect(await updatePage.processDefinitionKeyInput.getAttribute('value')).to.match(/modified/);

        await updatePage.taskNodeIdInput.clear();
        await updatePage.taskNodeIdInput.sendKeys('modified');
        expect(await updatePage.taskNodeIdInput.getAttribute('value')).to.match(/modified/);

        await updatePage.taskNodeNameInput.clear();
        await updatePage.taskNodeNameInput.sendKeys('modified');
        expect(await updatePage.taskNodeNameInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.commonTableIdInput);
        await updatePage.commonTableIdInput.sendKeys('6');
        expect(await updatePage.commonTableIdInput.getAttribute('value')).to.eq('6');

        await updatePage.formDataInput.clear();
        await updatePage.formDataInput.sendKeys('updatedformData');
        expect(await updatePage.formDataInput.getAttribute('value')).to.match(/updatedformData/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
