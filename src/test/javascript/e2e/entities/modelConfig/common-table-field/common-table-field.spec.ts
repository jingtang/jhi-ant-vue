/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import CommonTableFieldComponentsPage, { CommonTableFieldDeleteDialog } from './common-table-field.page-object';
import CommonTableFieldUpdatePage from './common-table-field-update.page-object';
import CommonTableFieldDetailsPage from './common-table-field-details.page-object';

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

describe('CommonTableField e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CommonTableFieldUpdatePage;
  let detailsPage: CommonTableFieldDetailsPage;
  let listPage: CommonTableFieldComponentsPage;
  let deleteDialog: CommonTableFieldDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CommonTableFields', async () => {
    await navBarPage.getEntityPage('common-table-field');
    listPage = new CommonTableFieldComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create CommonTableField page', async () => {
      await listPage.createButton.click();
      updatePage = new CommonTableFieldUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.modelConfigCommonTableField.home.createOrEditLabel/);
    });

    it('should create and save CommonTableFields', async () => {
      await updatePage.titleInput.sendKeys('title');
      expect(await updatePage.titleInput.getAttribute('value')).to.match(/title/);

      await updatePage.entityFieldNameInput.sendKeys('entityFieldName');
      expect(await updatePage.entityFieldNameInput.getAttribute('value')).to.match(/entityFieldName/);

      await selectLastOption(updatePage.typeSelect);

      await updatePage.tableColumnNameInput.sendKeys('tableColumnName');
      expect(await updatePage.tableColumnNameInput.getAttribute('value')).to.match(/tableColumnName/);

      await updatePage.columnWidthInput.sendKeys('5');
      expect(await updatePage.columnWidthInput.getAttribute('value')).to.eq('5');

      await updatePage.orderInput.sendKeys('5');
      expect(await updatePage.orderInput.getAttribute('value')).to.eq('5');

      const selectedEditInList = await updatePage.editInListInput.isSelected();
      if (selectedEditInList) {
        await updatePage.editInListInput.click();
        expect(await updatePage.editInListInput.isSelected()).to.be.false;
      } else {
        await updatePage.editInListInput.click();
        expect(await updatePage.editInListInput.isSelected()).to.be.true;
      }

      const selectedHideInList = await updatePage.hideInListInput.isSelected();
      if (selectedHideInList) {
        await updatePage.hideInListInput.click();
        expect(await updatePage.hideInListInput.isSelected()).to.be.false;
      } else {
        await updatePage.hideInListInput.click();
        expect(await updatePage.hideInListInput.isSelected()).to.be.true;
      }

      const selectedHideInForm = await updatePage.hideInFormInput.isSelected();
      if (selectedHideInForm) {
        await updatePage.hideInFormInput.click();
        expect(await updatePage.hideInFormInput.isSelected()).to.be.false;
      } else {
        await updatePage.hideInFormInput.click();
        expect(await updatePage.hideInFormInput.isSelected()).to.be.true;
      }

      const selectedEnableFilter = await updatePage.enableFilterInput.isSelected();
      if (selectedEnableFilter) {
        await updatePage.enableFilterInput.click();
        expect(await updatePage.enableFilterInput.isSelected()).to.be.false;
      } else {
        await updatePage.enableFilterInput.click();
        expect(await updatePage.enableFilterInput.isSelected()).to.be.true;
      }

      await updatePage.validateRulesInput.sendKeys('validateRules');
      expect(await updatePage.validateRulesInput.getAttribute('value')).to.match(/validateRules/);

      const selectedShowInFilterTree = await updatePage.showInFilterTreeInput.isSelected();
      if (selectedShowInFilterTree) {
        await updatePage.showInFilterTreeInput.click();
        expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.false;
      } else {
        await updatePage.showInFilterTreeInput.click();
        expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.true;
      }

      await selectLastOption(updatePage.fixedSelect);

      const selectedSortable = await updatePage.sortableInput.isSelected();
      if (selectedSortable) {
        await updatePage.sortableInput.click();
        expect(await updatePage.sortableInput.isSelected()).to.be.false;
      } else {
        await updatePage.sortableInput.click();
        expect(await updatePage.sortableInput.isSelected()).to.be.true;
      }

      const selectedTreeIndicator = await updatePage.treeIndicatorInput.isSelected();
      if (selectedTreeIndicator) {
        await updatePage.treeIndicatorInput.click();
        expect(await updatePage.treeIndicatorInput.isSelected()).to.be.false;
      } else {
        await updatePage.treeIndicatorInput.click();
        expect(await updatePage.treeIndicatorInput.isSelected()).to.be.true;
      }

      const selectedClientReadOnly = await updatePage.clientReadOnlyInput.isSelected();
      if (selectedClientReadOnly) {
        await updatePage.clientReadOnlyInput.click();
        expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.false;
      } else {
        await updatePage.clientReadOnlyInput.click();
        expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.true;
      }

      await updatePage.fieldValuesInput.sendKeys('fieldValues');
      expect(await updatePage.fieldValuesInput.getAttribute('value')).to.match(/fieldValues/);

      const selectedNotNull = await updatePage.notNullInput.isSelected();
      if (selectedNotNull) {
        await updatePage.notNullInput.click();
        expect(await updatePage.notNullInput.isSelected()).to.be.false;
      } else {
        await updatePage.notNullInput.click();
        expect(await updatePage.notNullInput.isSelected()).to.be.true;
      }

      const selectedSystem = await updatePage.systemInput.isSelected();
      if (selectedSystem) {
        await updatePage.systemInput.click();
        expect(await updatePage.systemInput.isSelected()).to.be.false;
      } else {
        await updatePage.systemInput.click();
        expect(await updatePage.systemInput.isSelected()).to.be.true;
      }

      await updatePage.helpInput.sendKeys('help');
      expect(await updatePage.helpInput.getAttribute('value')).to.match(/help/);

      await updatePage.fontColorInput.sendKeys('fontColor');
      expect(await updatePage.fontColorInput.getAttribute('value')).to.match(/fontColor/);

      await updatePage.backgroundColorInput.sendKeys('backgroundColor');
      expect(await updatePage.backgroundColorInput.getAttribute('value')).to.match(/backgroundColor/);

      // await  selectLastOption(updatePage.commonTableSelect);

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

        deleteDialog = new CommonTableFieldDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.modelConfigCommonTableField.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CommonTableField page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CommonTableFieldDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CommonTableField page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.titleInput.clear();
        await updatePage.titleInput.sendKeys('modified');
        expect(await updatePage.titleInput.getAttribute('value')).to.match(/modified/);

        await updatePage.entityFieldNameInput.clear();
        await updatePage.entityFieldNameInput.sendKeys('modified');
        expect(await updatePage.entityFieldNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.tableColumnNameInput.clear();
        await updatePage.tableColumnNameInput.sendKeys('modified');
        expect(await updatePage.tableColumnNameInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.columnWidthInput);
        await updatePage.columnWidthInput.sendKeys('6');
        expect(await updatePage.columnWidthInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.orderInput);
        await updatePage.orderInput.sendKeys('6');
        expect(await updatePage.orderInput.getAttribute('value')).to.eq('6');

        const selectedEditInList = await updatePage.editInListInput.isSelected();
        if (selectedEditInList) {
          await updatePage.editInListInput.click();
          expect(await updatePage.editInListInput.isSelected()).to.be.false;
        } else {
          await updatePage.editInListInput.click();
          expect(await updatePage.editInListInput.isSelected()).to.be.true;
        }

        const selectedHideInList = await updatePage.hideInListInput.isSelected();
        if (selectedHideInList) {
          await updatePage.hideInListInput.click();
          expect(await updatePage.hideInListInput.isSelected()).to.be.false;
        } else {
          await updatePage.hideInListInput.click();
          expect(await updatePage.hideInListInput.isSelected()).to.be.true;
        }

        const selectedHideInForm = await updatePage.hideInFormInput.isSelected();
        if (selectedHideInForm) {
          await updatePage.hideInFormInput.click();
          expect(await updatePage.hideInFormInput.isSelected()).to.be.false;
        } else {
          await updatePage.hideInFormInput.click();
          expect(await updatePage.hideInFormInput.isSelected()).to.be.true;
        }

        const selectedEnableFilter = await updatePage.enableFilterInput.isSelected();
        if (selectedEnableFilter) {
          await updatePage.enableFilterInput.click();
          expect(await updatePage.enableFilterInput.isSelected()).to.be.false;
        } else {
          await updatePage.enableFilterInput.click();
          expect(await updatePage.enableFilterInput.isSelected()).to.be.true;
        }

        await updatePage.validateRulesInput.clear();
        await updatePage.validateRulesInput.sendKeys('modified');
        expect(await updatePage.validateRulesInput.getAttribute('value')).to.match(/modified/);

        const selectedShowInFilterTree = await updatePage.showInFilterTreeInput.isSelected();
        if (selectedShowInFilterTree) {
          await updatePage.showInFilterTreeInput.click();
          expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.false;
        } else {
          await updatePage.showInFilterTreeInput.click();
          expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.true;
        }

        const selectedSortable = await updatePage.sortableInput.isSelected();
        if (selectedSortable) {
          await updatePage.sortableInput.click();
          expect(await updatePage.sortableInput.isSelected()).to.be.false;
        } else {
          await updatePage.sortableInput.click();
          expect(await updatePage.sortableInput.isSelected()).to.be.true;
        }

        const selectedTreeIndicator = await updatePage.treeIndicatorInput.isSelected();
        if (selectedTreeIndicator) {
          await updatePage.treeIndicatorInput.click();
          expect(await updatePage.treeIndicatorInput.isSelected()).to.be.false;
        } else {
          await updatePage.treeIndicatorInput.click();
          expect(await updatePage.treeIndicatorInput.isSelected()).to.be.true;
        }

        const selectedClientReadOnly = await updatePage.clientReadOnlyInput.isSelected();
        if (selectedClientReadOnly) {
          await updatePage.clientReadOnlyInput.click();
          expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.false;
        } else {
          await updatePage.clientReadOnlyInput.click();
          expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.true;
        }

        await updatePage.fieldValuesInput.clear();
        await updatePage.fieldValuesInput.sendKeys('modified');
        expect(await updatePage.fieldValuesInput.getAttribute('value')).to.match(/modified/);

        const selectedNotNull = await updatePage.notNullInput.isSelected();
        if (selectedNotNull) {
          await updatePage.notNullInput.click();
          expect(await updatePage.notNullInput.isSelected()).to.be.false;
        } else {
          await updatePage.notNullInput.click();
          expect(await updatePage.notNullInput.isSelected()).to.be.true;
        }

        const selectedSystem = await updatePage.systemInput.isSelected();
        if (selectedSystem) {
          await updatePage.systemInput.click();
          expect(await updatePage.systemInput.isSelected()).to.be.false;
        } else {
          await updatePage.systemInput.click();
          expect(await updatePage.systemInput.isSelected()).to.be.true;
        }

        await updatePage.helpInput.clear();
        await updatePage.helpInput.sendKeys('modified');
        expect(await updatePage.helpInput.getAttribute('value')).to.match(/modified/);

        await updatePage.fontColorInput.clear();
        await updatePage.fontColorInput.sendKeys('modified');
        expect(await updatePage.fontColorInput.getAttribute('value')).to.match(/modified/);

        await updatePage.backgroundColorInput.clear();
        await updatePage.backgroundColorInput.sendKeys('modified');
        expect(await updatePage.backgroundColorInput.getAttribute('value')).to.match(/modified/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
