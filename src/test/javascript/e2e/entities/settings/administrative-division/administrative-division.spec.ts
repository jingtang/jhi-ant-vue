/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import AdministrativeDivisionComponentsPage, { AdministrativeDivisionDeleteDialog } from './administrative-division.page-object';
import AdministrativeDivisionUpdatePage from './administrative-division-update.page-object';
import AdministrativeDivisionDetailsPage from './administrative-division-details.page-object';

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

describe('AdministrativeDivision e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: AdministrativeDivisionUpdatePage;
  let detailsPage: AdministrativeDivisionDetailsPage;
  let listPage: AdministrativeDivisionComponentsPage;
  let deleteDialog: AdministrativeDivisionDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load AdministrativeDivisions', async () => {
    await navBarPage.getEntityPage('administrative-division');
    listPage = new AdministrativeDivisionComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create AdministrativeDivision page', async () => {
      await listPage.createButton.click();
      updatePage = new AdministrativeDivisionUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.settingsAdministrativeDivision.home.createOrEditLabel/);
    });

    it('should create and save AdministrativeDivisions', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.areaCodeInput.sendKeys('areaCode');
      expect(await updatePage.areaCodeInput.getAttribute('value')).to.match(/areaCode/);

      await updatePage.cityCodeInput.sendKeys('cityCode');
      expect(await updatePage.cityCodeInput.getAttribute('value')).to.match(/cityCode/);

      await updatePage.mergerNameInput.sendKeys('mergerName');
      expect(await updatePage.mergerNameInput.getAttribute('value')).to.match(/mergerName/);

      await updatePage.shortNameInput.sendKeys('shortName');
      expect(await updatePage.shortNameInput.getAttribute('value')).to.match(/shortName/);

      await updatePage.zipCodeInput.sendKeys('zipCode');
      expect(await updatePage.zipCodeInput.getAttribute('value')).to.match(/zipCode/);

      await updatePage.levelInput.sendKeys('5');
      expect(await updatePage.levelInput.getAttribute('value')).to.eq('5');

      await updatePage.lngInput.sendKeys('5');
      expect(await updatePage.lngInput.getAttribute('value')).to.eq('5');

      await updatePage.latInput.sendKeys('5');
      expect(await updatePage.latInput.getAttribute('value')).to.eq('5');

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

        deleteDialog = new AdministrativeDivisionDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.settingsAdministrativeDivision.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details AdministrativeDivision page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new AdministrativeDivisionDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit AdministrativeDivision page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.areaCodeInput.clear();
        await updatePage.areaCodeInput.sendKeys('modified');
        expect(await updatePage.areaCodeInput.getAttribute('value')).to.match(/modified/);

        await updatePage.cityCodeInput.clear();
        await updatePage.cityCodeInput.sendKeys('modified');
        expect(await updatePage.cityCodeInput.getAttribute('value')).to.match(/modified/);

        await updatePage.mergerNameInput.clear();
        await updatePage.mergerNameInput.sendKeys('modified');
        expect(await updatePage.mergerNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.shortNameInput.clear();
        await updatePage.shortNameInput.sendKeys('modified');
        expect(await updatePage.shortNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.zipCodeInput.clear();
        await updatePage.zipCodeInput.sendKeys('modified');
        expect(await updatePage.zipCodeInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.levelInput);
        await updatePage.levelInput.sendKeys('6');
        expect(await updatePage.levelInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.lngInput);
        await updatePage.lngInput.sendKeys('6');
        expect(await updatePage.lngInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.latInput);
        await updatePage.latInput.sendKeys('6');
        expect(await updatePage.latInput.getAttribute('value')).to.eq('6');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
