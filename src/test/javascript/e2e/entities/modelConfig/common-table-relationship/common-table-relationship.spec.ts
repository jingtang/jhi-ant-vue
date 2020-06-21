/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import CommonTableRelationshipComponentsPage, { CommonTableRelationshipDeleteDialog } from './common-table-relationship.page-object';
import CommonTableRelationshipUpdatePage from './common-table-relationship-update.page-object';
import CommonTableRelationshipDetailsPage from './common-table-relationship-details.page-object';

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

describe('CommonTableRelationship e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CommonTableRelationshipUpdatePage;
  let detailsPage: CommonTableRelationshipDetailsPage;
  let listPage: CommonTableRelationshipComponentsPage;
  let deleteDialog: CommonTableRelationshipDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CommonTableRelationships', async () => {
    await navBarPage.getEntityPage('common-table-relationship');
    listPage = new CommonTableRelationshipComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create CommonTableRelationship page', async () => {
      await listPage.createButton.click();
      updatePage = new CommonTableRelationshipUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.modelConfigCommonTableRelationship.home.createOrEditLabel/);
    });

    it('should create and save CommonTableRelationships', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await selectLastOption(updatePage.relationshipTypeSelect);

      await selectLastOption(updatePage.sourceTypeSelect);

      await updatePage.otherEntityFieldInput.sendKeys('otherEntityField');
      expect(await updatePage.otherEntityFieldInput.getAttribute('value')).to.match(/otherEntityField/);

      await updatePage.otherEntityNameInput.sendKeys('otherEntityName');
      expect(await updatePage.otherEntityNameInput.getAttribute('value')).to.match(/otherEntityName/);

      await updatePage.relationshipNameInput.sendKeys('relationshipName');
      expect(await updatePage.relationshipNameInput.getAttribute('value')).to.match(/relationshipName/);

      await updatePage.otherEntityRelationshipNameInput.sendKeys('otherEntityRelationshipName');
      expect(await updatePage.otherEntityRelationshipNameInput.getAttribute('value')).to.match(/otherEntityRelationshipName/);

      await updatePage.columnWidthInput.sendKeys('5');
      expect(await updatePage.columnWidthInput.getAttribute('value')).to.eq('5');

      await updatePage.orderInput.sendKeys('5');
      expect(await updatePage.orderInput.getAttribute('value')).to.eq('5');

      await selectLastOption(updatePage.fixedSelect);

      const selectedEditInList = await updatePage.editInListInput.isSelected();
      if (selectedEditInList) {
        await updatePage.editInListInput.click();
        expect(await updatePage.editInListInput.isSelected()).to.be.false;
      } else {
        await updatePage.editInListInput.click();
        expect(await updatePage.editInListInput.isSelected()).to.be.true;
      }

      const selectedEnableFilter = await updatePage.enableFilterInput.isSelected();
      if (selectedEnableFilter) {
        await updatePage.enableFilterInput.click();
        expect(await updatePage.enableFilterInput.isSelected()).to.be.false;
      } else {
        await updatePage.enableFilterInput.click();
        expect(await updatePage.enableFilterInput.isSelected()).to.be.true;
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

      await updatePage.fontColorInput.sendKeys('fontColor');
      expect(await updatePage.fontColorInput.getAttribute('value')).to.match(/fontColor/);

      await updatePage.backgroundColorInput.sendKeys('backgroundColor');
      expect(await updatePage.backgroundColorInput.getAttribute('value')).to.match(/backgroundColor/);

      await updatePage.helpInput.sendKeys('help');
      expect(await updatePage.helpInput.getAttribute('value')).to.match(/help/);

      const selectedOwnerSide = await updatePage.ownerSideInput.isSelected();
      if (selectedOwnerSide) {
        await updatePage.ownerSideInput.click();
        expect(await updatePage.ownerSideInput.isSelected()).to.be.false;
      } else {
        await updatePage.ownerSideInput.click();
        expect(await updatePage.ownerSideInput.isSelected()).to.be.true;
      }

      await updatePage.dataNameInput.sendKeys('dataName');
      expect(await updatePage.dataNameInput.getAttribute('value')).to.match(/dataName/);

      await updatePage.webComponentTypeInput.sendKeys('webComponentType');
      expect(await updatePage.webComponentTypeInput.getAttribute('value')).to.match(/webComponentType/);

      const selectedOtherEntityIsTree = await updatePage.otherEntityIsTreeInput.isSelected();
      if (selectedOtherEntityIsTree) {
        await updatePage.otherEntityIsTreeInput.click();
        expect(await updatePage.otherEntityIsTreeInput.isSelected()).to.be.false;
      } else {
        await updatePage.otherEntityIsTreeInput.click();
        expect(await updatePage.otherEntityIsTreeInput.isSelected()).to.be.true;
      }

      const selectedShowInFilterTree = await updatePage.showInFilterTreeInput.isSelected();
      if (selectedShowInFilterTree) {
        await updatePage.showInFilterTreeInput.click();
        expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.false;
      } else {
        await updatePage.showInFilterTreeInput.click();
        expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.true;
      }

      await updatePage.dataDictionaryCodeInput.sendKeys('dataDictionaryCode');
      expect(await updatePage.dataDictionaryCodeInput.getAttribute('value')).to.match(/dataDictionaryCode/);

      const selectedClientReadOnly = await updatePage.clientReadOnlyInput.isSelected();
      if (selectedClientReadOnly) {
        await updatePage.clientReadOnlyInput.click();
        expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.false;
      } else {
        await updatePage.clientReadOnlyInput.click();
        expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.relationEntitySelect);
      // await  selectLastOption(updatePage.dataDictionaryNodeSelect);
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

        deleteDialog = new CommonTableRelationshipDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.modelConfigCommonTableRelationship.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CommonTableRelationship page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CommonTableRelationshipDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CommonTableRelationship page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.otherEntityFieldInput.clear();
        await updatePage.otherEntityFieldInput.sendKeys('modified');
        expect(await updatePage.otherEntityFieldInput.getAttribute('value')).to.match(/modified/);

        await updatePage.otherEntityNameInput.clear();
        await updatePage.otherEntityNameInput.sendKeys('modified');
        expect(await updatePage.otherEntityNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.relationshipNameInput.clear();
        await updatePage.relationshipNameInput.sendKeys('modified');
        expect(await updatePage.relationshipNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.otherEntityRelationshipNameInput.clear();
        await updatePage.otherEntityRelationshipNameInput.sendKeys('modified');
        expect(await updatePage.otherEntityRelationshipNameInput.getAttribute('value')).to.match(/modified/);

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

        const selectedEnableFilter = await updatePage.enableFilterInput.isSelected();
        if (selectedEnableFilter) {
          await updatePage.enableFilterInput.click();
          expect(await updatePage.enableFilterInput.isSelected()).to.be.false;
        } else {
          await updatePage.enableFilterInput.click();
          expect(await updatePage.enableFilterInput.isSelected()).to.be.true;
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

        await updatePage.fontColorInput.clear();
        await updatePage.fontColorInput.sendKeys('modified');
        expect(await updatePage.fontColorInput.getAttribute('value')).to.match(/modified/);

        await updatePage.backgroundColorInput.clear();
        await updatePage.backgroundColorInput.sendKeys('modified');
        expect(await updatePage.backgroundColorInput.getAttribute('value')).to.match(/modified/);

        await updatePage.helpInput.clear();
        await updatePage.helpInput.sendKeys('modified');
        expect(await updatePage.helpInput.getAttribute('value')).to.match(/modified/);

        const selectedOwnerSide = await updatePage.ownerSideInput.isSelected();
        if (selectedOwnerSide) {
          await updatePage.ownerSideInput.click();
          expect(await updatePage.ownerSideInput.isSelected()).to.be.false;
        } else {
          await updatePage.ownerSideInput.click();
          expect(await updatePage.ownerSideInput.isSelected()).to.be.true;
        }

        await updatePage.dataNameInput.clear();
        await updatePage.dataNameInput.sendKeys('modified');
        expect(await updatePage.dataNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.webComponentTypeInput.clear();
        await updatePage.webComponentTypeInput.sendKeys('modified');
        expect(await updatePage.webComponentTypeInput.getAttribute('value')).to.match(/modified/);

        const selectedOtherEntityIsTree = await updatePage.otherEntityIsTreeInput.isSelected();
        if (selectedOtherEntityIsTree) {
          await updatePage.otherEntityIsTreeInput.click();
          expect(await updatePage.otherEntityIsTreeInput.isSelected()).to.be.false;
        } else {
          await updatePage.otherEntityIsTreeInput.click();
          expect(await updatePage.otherEntityIsTreeInput.isSelected()).to.be.true;
        }

        const selectedShowInFilterTree = await updatePage.showInFilterTreeInput.isSelected();
        if (selectedShowInFilterTree) {
          await updatePage.showInFilterTreeInput.click();
          expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.false;
        } else {
          await updatePage.showInFilterTreeInput.click();
          expect(await updatePage.showInFilterTreeInput.isSelected()).to.be.true;
        }

        await updatePage.dataDictionaryCodeInput.clear();
        await updatePage.dataDictionaryCodeInput.sendKeys('modified');
        expect(await updatePage.dataDictionaryCodeInput.getAttribute('value')).to.match(/modified/);

        const selectedClientReadOnly = await updatePage.clientReadOnlyInput.isSelected();
        if (selectedClientReadOnly) {
          await updatePage.clientReadOnlyInput.click();
          expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.false;
        } else {
          await updatePage.clientReadOnlyInput.click();
          expect(await updatePage.clientReadOnlyInput.isSelected()).to.be.true;
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
