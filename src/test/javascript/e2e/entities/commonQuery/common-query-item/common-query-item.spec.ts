/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import CommonQueryItemComponentsPage, { CommonQueryItemDeleteDialog } from './common-query-item.page-object';
import CommonQueryItemUpdatePage from './common-query-item-update.page-object';
import CommonQueryItemDetailsPage from './common-query-item-details.page-object';

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

describe('CommonQueryItem e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CommonQueryItemUpdatePage;
  let detailsPage: CommonQueryItemDetailsPage;
  let listPage: CommonQueryItemComponentsPage;
  let deleteDialog: CommonQueryItemDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CommonQueryItems', async () => {
    await navBarPage.getEntityPage('common-query-item');
    listPage = new CommonQueryItemComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create CommonQueryItem page', async () => {
      await listPage.createButton.click();
      updatePage = new CommonQueryItemUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.commonQueryCommonQueryItem.home.createOrEditLabel/);
    });

    it('should create and save CommonQueryItems', async () => {
      await updatePage.prefixInput.sendKeys('prefix');
      expect(await updatePage.prefixInput.getAttribute('value')).to.match(/prefix/);

      await updatePage.fieldNameInput.sendKeys('fieldName');
      expect(await updatePage.fieldNameInput.getAttribute('value')).to.match(/fieldName/);

      await updatePage.fieldTypeInput.sendKeys('fieldType');
      expect(await updatePage.fieldTypeInput.getAttribute('value')).to.match(/fieldType/);

      await updatePage.operatorInput.sendKeys('operator');
      expect(await updatePage.operatorInput.getAttribute('value')).to.match(/operator/);

      await updatePage.valueInput.sendKeys('value');
      expect(await updatePage.valueInput.getAttribute('value')).to.match(/value/);

      await updatePage.suffixInput.sendKeys('suffix');
      expect(await updatePage.suffixInput.getAttribute('value')).to.match(/suffix/);

      await updatePage.orderInput.sendKeys('5');
      expect(await updatePage.orderInput.getAttribute('value')).to.eq('5');

      // await  selectLastOption(updatePage.querySelect);

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

        deleteDialog = new CommonQueryItemDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.commonQueryCommonQueryItem.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CommonQueryItem page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CommonQueryItemDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CommonQueryItem page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.prefixInput.clear();
        await updatePage.prefixInput.sendKeys('modified');
        expect(await updatePage.prefixInput.getAttribute('value')).to.match(/modified/);

        await updatePage.fieldNameInput.clear();
        await updatePage.fieldNameInput.sendKeys('modified');
        expect(await updatePage.fieldNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.fieldTypeInput.clear();
        await updatePage.fieldTypeInput.sendKeys('modified');
        expect(await updatePage.fieldTypeInput.getAttribute('value')).to.match(/modified/);

        await updatePage.operatorInput.clear();
        await updatePage.operatorInput.sendKeys('modified');
        expect(await updatePage.operatorInput.getAttribute('value')).to.match(/modified/);

        await updatePage.valueInput.clear();
        await updatePage.valueInput.sendKeys('modified');
        expect(await updatePage.valueInput.getAttribute('value')).to.match(/modified/);

        await updatePage.suffixInput.clear();
        await updatePage.suffixInput.sendKeys('modified');
        expect(await updatePage.suffixInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.orderInput);
        await updatePage.orderInput.sendKeys('6');
        expect(await updatePage.orderInput.getAttribute('value')).to.eq('6');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
