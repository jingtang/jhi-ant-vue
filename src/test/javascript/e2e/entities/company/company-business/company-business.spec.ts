/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import CompanyBusinessComponentsPage, { CompanyBusinessDeleteDialog } from './company-business.page-object';
import CompanyBusinessUpdatePage from './company-business-update.page-object';
import CompanyBusinessDetailsPage from './company-business-details.page-object';

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

describe('CompanyBusiness e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CompanyBusinessUpdatePage;
  let detailsPage: CompanyBusinessDetailsPage;
  let listPage: CompanyBusinessComponentsPage;
  let deleteDialog: CompanyBusinessDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CompanyBusinesses', async () => {
    await navBarPage.getEntityPage('company-business');
    listPage = new CompanyBusinessComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create CompanyBusiness page', async () => {
      await listPage.createButton.click();
      updatePage = new CompanyBusinessUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.companyCompanyBusiness.home.createOrEditLabel/);
    });

    it('should create and save CompanyBusinesses', async () => {
      await selectLastOption(updatePage.statusSelect);

      await updatePage.expirationTimeInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.expirationTimeInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      await updatePage.startTimeInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.startTimeInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      await updatePage.operateUserIdInput.sendKeys('5');
      expect(await updatePage.operateUserIdInput.getAttribute('value')).to.eq('5');

      await updatePage.groupIdInput.sendKeys('groupId');
      expect(await updatePage.groupIdInput.getAttribute('value')).to.match(/groupId/);

      // await  selectLastOption(updatePage.businessTypeSelect);
      // await  selectLastOption(updatePage.companySelect);

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

        deleteDialog = new CompanyBusinessDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.companyCompanyBusiness.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CompanyBusiness page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CompanyBusinessDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CompanyBusiness page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.expirationTimeInput.clear();
        await updatePage.expirationTimeInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.expirationTimeInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await updatePage.startTimeInput.clear();
        await updatePage.startTimeInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.startTimeInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await clear(updatePage.operateUserIdInput);
        await updatePage.operateUserIdInput.sendKeys('6');
        expect(await updatePage.operateUserIdInput.getAttribute('value')).to.eq('6');

        await updatePage.groupIdInput.clear();
        await updatePage.groupIdInput.sendKeys('modified');
        expect(await updatePage.groupIdInput.getAttribute('value')).to.match(/modified/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
