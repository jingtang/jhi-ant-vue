/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import GpsInfoComponentsPage, { GpsInfoDeleteDialog } from './gps-info.page-object';
import GpsInfoUpdatePage from './gps-info-update.page-object';
import GpsInfoDetailsPage from './gps-info-details.page-object';

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

describe('GpsInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: GpsInfoUpdatePage;
  let detailsPage: GpsInfoDetailsPage;
  let listPage: GpsInfoComponentsPage;
  let deleteDialog: GpsInfoDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load GpsInfos', async () => {
    await navBarPage.getEntityPage('gps-info');
    listPage = new GpsInfoComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create GpsInfo page', async () => {
      await listPage.createButton.click();
      updatePage = new GpsInfoUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.settingsGpsInfo.home.createOrEditLabel/);
    });

    it('should create and save GpsInfos', async () => {
      await selectLastOption(updatePage.typeSelect);

      await updatePage.latitudeInput.sendKeys('5');
      expect(await updatePage.latitudeInput.getAttribute('value')).to.eq('5');

      await updatePage.longitudeInput.sendKeys('5');
      expect(await updatePage.longitudeInput.getAttribute('value')).to.eq('5');

      await updatePage.addressInput.sendKeys('address');
      expect(await updatePage.addressInput.getAttribute('value')).to.match(/address/);

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

        deleteDialog = new GpsInfoDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.settingsGpsInfo.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details GpsInfo page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new GpsInfoDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit GpsInfo page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await clear(updatePage.latitudeInput);
        await updatePage.latitudeInput.sendKeys('6');
        expect(await updatePage.latitudeInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.longitudeInput);
        await updatePage.longitudeInput.sendKeys('6');
        expect(await updatePage.longitudeInput.getAttribute('value')).to.eq('6');

        await updatePage.addressInput.clear();
        await updatePage.addressInput.sendKeys('modified');
        expect(await updatePage.addressInput.getAttribute('value')).to.match(/modified/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
