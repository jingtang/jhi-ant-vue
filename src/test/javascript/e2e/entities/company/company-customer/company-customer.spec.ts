/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import CompanyCustomerComponentsPage, { CompanyCustomerDeleteDialog } from './company-customer.page-object';
import CompanyCustomerUpdatePage from './company-customer-update.page-object';
import CompanyCustomerDetailsPage from './company-customer-details.page-object';

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

describe('CompanyCustomer e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CompanyCustomerUpdatePage;
  let detailsPage: CompanyCustomerDetailsPage;
  let listPage: CompanyCustomerComponentsPage;
  let deleteDialog: CompanyCustomerDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CompanyCustomers', async () => {
    await navBarPage.getEntityPage('company-customer');
    listPage = new CompanyCustomerComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create CompanyCustomer page', async () => {
      await listPage.createButton.click();
      updatePage = new CompanyCustomerUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.companyCompanyCustomer.home.createOrEditLabel/);
    });

    it('should create and save CompanyCustomers', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.codeInput.sendKeys('code');
      expect(await updatePage.codeInput.getAttribute('value')).to.match(/code/);

      await updatePage.addressInput.sendKeys('address');
      expect(await updatePage.addressInput.getAttribute('value')).to.match(/address/);

      await updatePage.phoneNumInput.sendKeys('phoneNum');
      expect(await updatePage.phoneNumInput.getAttribute('value')).to.match(/phoneNum/);

      await updatePage.logoInput.sendKeys('logo');
      expect(await updatePage.logoInput.getAttribute('value')).to.match(/logo/);

      await updatePage.contactInput.sendKeys('contact');
      expect(await updatePage.contactInput.getAttribute('value')).to.match(/contact/);

      await updatePage.createUserIdInput.sendKeys('5');
      expect(await updatePage.createUserIdInput.getAttribute('value')).to.eq('5');

      await updatePage.createTimeInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.createTimeInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      // await  selectLastOption(updatePage.parentSelect);

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

        deleteDialog = new CompanyCustomerDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.companyCompanyCustomer.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CompanyCustomer page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CompanyCustomerDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CompanyCustomer page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.codeInput.clear();
        await updatePage.codeInput.sendKeys('modified');
        expect(await updatePage.codeInput.getAttribute('value')).to.match(/modified/);

        await updatePage.addressInput.clear();
        await updatePage.addressInput.sendKeys('modified');
        expect(await updatePage.addressInput.getAttribute('value')).to.match(/modified/);

        await updatePage.phoneNumInput.clear();
        await updatePage.phoneNumInput.sendKeys('modified');
        expect(await updatePage.phoneNumInput.getAttribute('value')).to.match(/modified/);

        await updatePage.logoInput.clear();
        await updatePage.logoInput.sendKeys('modified');
        expect(await updatePage.logoInput.getAttribute('value')).to.match(/modified/);

        await updatePage.contactInput.clear();
        await updatePage.contactInput.sendKeys('modified');
        expect(await updatePage.contactInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.createUserIdInput);
        await updatePage.createUserIdInput.sendKeys('6');
        expect(await updatePage.createUserIdInput.getAttribute('value')).to.eq('6');

        await updatePage.createTimeInput.clear();
        await updatePage.createTimeInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.createTimeInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
