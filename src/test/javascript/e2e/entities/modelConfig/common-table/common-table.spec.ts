/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import CommonTableComponentsPage, { CommonTableDeleteDialog } from './common-table.page-object';
import CommonTableUpdatePage from './common-table-update.page-object';
import CommonTableDetailsPage from './common-table-details.page-object';

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

describe('CommonTable e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CommonTableUpdatePage;
  let detailsPage: CommonTableDetailsPage;
  let listPage: CommonTableComponentsPage;
  let deleteDialog: CommonTableDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CommonTables', async () => {
    await navBarPage.getEntityPage('common-table');
    listPage = new CommonTableComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create CommonTable page', async () => {
      await listPage.createButton.click();
      updatePage = new CommonTableUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.modelConfigCommonTable.home.createOrEditLabel/);
    });

    it('should create and save CommonTables', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.entityNameInput.sendKeys('entityName');
      expect(await updatePage.entityNameInput.getAttribute('value')).to.match(/entityName/);

      await updatePage.tableNameInput.sendKeys('tableName');
      expect(await updatePage.tableNameInput.getAttribute('value')).to.match(/tableName/);

      const selectedSystem = await updatePage.systemInput.isSelected();
      if (selectedSystem) {
        await updatePage.systemInput.click();
        expect(await updatePage.systemInput.isSelected()).to.be.false;
      } else {
        await updatePage.systemInput.click();
        expect(await updatePage.systemInput.isSelected()).to.be.true;
      }

      await updatePage.clazzNameInput.sendKeys('clazzName');
      expect(await updatePage.clazzNameInput.getAttribute('value')).to.match(/clazzName/);

      const selectedGenerated = await updatePage.generatedInput.isSelected();
      if (selectedGenerated) {
        await updatePage.generatedInput.click();
        expect(await updatePage.generatedInput.isSelected()).to.be.false;
      } else {
        await updatePage.generatedInput.click();
        expect(await updatePage.generatedInput.isSelected()).to.be.true;
      }

      await updatePage.creatAtInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.creatAtInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      await updatePage.generateAtInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.generateAtInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      await updatePage.generateClassAtInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.generateClassAtInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);

      const selectedTreeTable = await updatePage.treeTableInput.isSelected();
      if (selectedTreeTable) {
        await updatePage.treeTableInput.click();
        expect(await updatePage.treeTableInput.isSelected()).to.be.false;
      } else {
        await updatePage.treeTableInput.click();
        expect(await updatePage.treeTableInput.isSelected()).to.be.true;
      }

      await updatePage.baseTableIdInput.sendKeys('5');
      expect(await updatePage.baseTableIdInput.getAttribute('value')).to.eq('5');

      await updatePage.recordActionWidthInput.sendKeys('5');
      expect(await updatePage.recordActionWidthInput.getAttribute('value')).to.eq('5');

      await waitUntilDisplayed(updatePage.listConfigInput);
      await updatePage.listConfigInput.sendKeys('listConfig');

      expect(await updatePage.listConfigInput.getAttribute('value')).to.match(/listConfig/);

      await waitUntilDisplayed(updatePage.formConfigInput);
      await updatePage.formConfigInput.sendKeys('formConfig');

      expect(await updatePage.formConfigInput.getAttribute('value')).to.match(/formConfig/);

      // await  selectLastOption(updatePage.creatorSelect);
      // await  selectLastOption(updatePage.businessTypeSelect);

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

        deleteDialog = new CommonTableDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.modelConfigCommonTable.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CommonTable page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CommonTableDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CommonTable page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.entityNameInput.clear();
        await updatePage.entityNameInput.sendKeys('modified');
        expect(await updatePage.entityNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.tableNameInput.clear();
        await updatePage.tableNameInput.sendKeys('modified');
        expect(await updatePage.tableNameInput.getAttribute('value')).to.match(/modified/);

        const selectedSystem = await updatePage.systemInput.isSelected();
        if (selectedSystem) {
          await updatePage.systemInput.click();
          expect(await updatePage.systemInput.isSelected()).to.be.false;
        } else {
          await updatePage.systemInput.click();
          expect(await updatePage.systemInput.isSelected()).to.be.true;
        }

        await updatePage.clazzNameInput.clear();
        await updatePage.clazzNameInput.sendKeys('modified');
        expect(await updatePage.clazzNameInput.getAttribute('value')).to.match(/modified/);

        const selectedGenerated = await updatePage.generatedInput.isSelected();
        if (selectedGenerated) {
          await updatePage.generatedInput.click();
          expect(await updatePage.generatedInput.isSelected()).to.be.false;
        } else {
          await updatePage.generatedInput.click();
          expect(await updatePage.generatedInput.isSelected()).to.be.true;
        }

        await updatePage.creatAtInput.clear();
        await updatePage.creatAtInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.creatAtInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await updatePage.generateAtInput.clear();
        await updatePage.generateAtInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.generateAtInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await updatePage.generateClassAtInput.clear();
        await updatePage.generateClassAtInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.generateClassAtInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await updatePage.descriptionInput.clear();
        await updatePage.descriptionInput.sendKeys('modified');
        expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/modified/);

        const selectedTreeTable = await updatePage.treeTableInput.isSelected();
        if (selectedTreeTable) {
          await updatePage.treeTableInput.click();
          expect(await updatePage.treeTableInput.isSelected()).to.be.false;
        } else {
          await updatePage.treeTableInput.click();
          expect(await updatePage.treeTableInput.isSelected()).to.be.true;
        }

        await clear(updatePage.baseTableIdInput);
        await updatePage.baseTableIdInput.sendKeys('6');
        expect(await updatePage.baseTableIdInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.recordActionWidthInput);
        await updatePage.recordActionWidthInput.sendKeys('6');
        expect(await updatePage.recordActionWidthInput.getAttribute('value')).to.eq('6');

        await updatePage.listConfigInput.clear();
        await updatePage.listConfigInput.sendKeys('updatedlistConfig');
        expect(await updatePage.listConfigInput.getAttribute('value')).to.match(/updatedlistConfig/);

        await updatePage.formConfigInput.clear();
        await updatePage.formConfigInput.sendKeys('updatedformConfig');
        expect(await updatePage.formConfigInput.getAttribute('value')).to.match(/updatedformConfig/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
