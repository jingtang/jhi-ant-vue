/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import ProcessTableConfigComponentsPage, { ProcessTableConfigDeleteDialog } from './process-table-config.page-object';
import ProcessTableConfigUpdatePage from './process-table-config-update.page-object';
import ProcessTableConfigDetailsPage from './process-table-config-details.page-object';

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

describe('ProcessTableConfig e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ProcessTableConfigUpdatePage;
  let detailsPage: ProcessTableConfigDetailsPage;
  let listPage: ProcessTableConfigComponentsPage;
  let deleteDialog: ProcessTableConfigDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load ProcessTableConfigs', async () => {
    await navBarPage.getEntityPage('process-table-config');
    listPage = new ProcessTableConfigComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create ProcessTableConfig page', async () => {
      await listPage.createButton.click();
      updatePage = new ProcessTableConfigUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.workflowProcessTableConfig.home.createOrEditLabel/);
    });

    it('should create and save ProcessTableConfigs', async () => {
      await updatePage.processDefinitionIdInput.sendKeys('processDefinitionId');
      expect(await updatePage.processDefinitionIdInput.getAttribute('value')).to.match(/processDefinitionId/);

      await updatePage.processDefinitionKeyInput.sendKeys('processDefinitionKey');
      expect(await updatePage.processDefinitionKeyInput.getAttribute('value')).to.match(/processDefinitionKey/);

      await updatePage.processDefinitionNameInput.sendKeys('processDefinitionName');
      expect(await updatePage.processDefinitionNameInput.getAttribute('value')).to.match(/processDefinitionName/);

      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);

      await waitUntilDisplayed(updatePage.processBpmnDataInput);
      await updatePage.processBpmnDataInput.sendKeys('processBpmnData');

      expect(await updatePage.processBpmnDataInput.getAttribute('value')).to.match(/processBpmnData/);

      const selectedDeploied = await updatePage.deploiedInput.isSelected();
      if (selectedDeploied) {
        await updatePage.deploiedInput.click();
        expect(await updatePage.deploiedInput.isSelected()).to.be.false;
      } else {
        await updatePage.deploiedInput.click();
        expect(await updatePage.deploiedInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.commonTableSelect);
      // await  selectLastOption(updatePage.creatorSelect);

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

        deleteDialog = new ProcessTableConfigDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.workflowProcessTableConfig.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details ProcessTableConfig page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ProcessTableConfigDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit ProcessTableConfig page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.processDefinitionIdInput.clear();
        await updatePage.processDefinitionIdInput.sendKeys('modified');
        expect(await updatePage.processDefinitionIdInput.getAttribute('value')).to.match(/modified/);

        await updatePage.processDefinitionKeyInput.clear();
        await updatePage.processDefinitionKeyInput.sendKeys('modified');
        expect(await updatePage.processDefinitionKeyInput.getAttribute('value')).to.match(/modified/);

        await updatePage.processDefinitionNameInput.clear();
        await updatePage.processDefinitionNameInput.sendKeys('modified');
        expect(await updatePage.processDefinitionNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.descriptionInput.clear();
        await updatePage.descriptionInput.sendKeys('modified');
        expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/modified/);

        await updatePage.processBpmnDataInput.clear();
        await updatePage.processBpmnDataInput.sendKeys('updatedprocessBpmnData');
        expect(await updatePage.processBpmnDataInput.getAttribute('value')).to.match(/updatedprocessBpmnData/);

        const selectedDeploied = await updatePage.deploiedInput.isSelected();
        if (selectedDeploied) {
          await updatePage.deploiedInput.click();
          expect(await updatePage.deploiedInput.isSelected()).to.be.false;
        } else {
          await updatePage.deploiedInput.click();
          expect(await updatePage.deploiedInput.isSelected()).to.be.true;
        }

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
