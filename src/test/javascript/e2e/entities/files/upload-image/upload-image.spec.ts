/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import UploadImageComponentsPage, { UploadImageDeleteDialog } from './upload-image.page-object';
import UploadImageUpdatePage from './upload-image-update.page-object';
import UploadImageDetailsPage from './upload-image-details.page-object';

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

describe('UploadImage e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: UploadImageUpdatePage;
  let detailsPage: UploadImageDetailsPage;
  let listPage: UploadImageComponentsPage;
  let deleteDialog: UploadImageDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load UploadImages', async () => {
    await navBarPage.getEntityPage('upload-image');
    listPage = new UploadImageComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create UploadImage page', async () => {
      await listPage.createButton.click();
      updatePage = new UploadImageUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.filesUploadImage.home.createOrEditLabel/);
    });

    it('should create and save UploadImages', async () => {
      await updatePage.fullNameInput.sendKeys('fullName');
      expect(await updatePage.fullNameInput.getAttribute('value')).to.match(/fullName/);

      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.extInput.sendKeys('ext');
      expect(await updatePage.extInput.getAttribute('value')).to.match(/ext/);

      await updatePage.typeInput.sendKeys('type');
      expect(await updatePage.typeInput.getAttribute('value')).to.match(/type/);

      await updatePage.urlInput.sendKeys('url');
      expect(await updatePage.urlInput.getAttribute('value')).to.match(/url/);

      await updatePage.pathInput.sendKeys('path');
      expect(await updatePage.pathInput.getAttribute('value')).to.match(/path/);

      await updatePage.folderInput.sendKeys('folder');
      expect(await updatePage.folderInput.getAttribute('value')).to.match(/folder/);

      await updatePage.entityNameInput.sendKeys('entityName');
      expect(await updatePage.entityNameInput.getAttribute('value')).to.match(/entityName/);

      await updatePage.createAtInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.createAtInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      await updatePage.fileSizeInput.sendKeys('5');
      expect(await updatePage.fileSizeInput.getAttribute('value')).to.eq('5');

      await updatePage.smartUrlInput.sendKeys('smartUrl');
      expect(await updatePage.smartUrlInput.getAttribute('value')).to.match(/smartUrl/);

      await updatePage.mediumUrlInput.sendKeys('mediumUrl');
      expect(await updatePage.mediumUrlInput.getAttribute('value')).to.match(/mediumUrl/);

      await updatePage.referenceCountInput.sendKeys('5');
      expect(await updatePage.referenceCountInput.getAttribute('value')).to.eq('5');

      // await  selectLastOption(updatePage.userSelect);

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

        deleteDialog = new UploadImageDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.filesUploadImage.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details UploadImage page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new UploadImageDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit UploadImage page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.fullNameInput.clear();
        await updatePage.fullNameInput.sendKeys('modified');
        expect(await updatePage.fullNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.extInput.clear();
        await updatePage.extInput.sendKeys('modified');
        expect(await updatePage.extInput.getAttribute('value')).to.match(/modified/);

        await updatePage.typeInput.clear();
        await updatePage.typeInput.sendKeys('modified');
        expect(await updatePage.typeInput.getAttribute('value')).to.match(/modified/);

        await updatePage.urlInput.clear();
        await updatePage.urlInput.sendKeys('modified');
        expect(await updatePage.urlInput.getAttribute('value')).to.match(/modified/);

        await updatePage.pathInput.clear();
        await updatePage.pathInput.sendKeys('modified');
        expect(await updatePage.pathInput.getAttribute('value')).to.match(/modified/);

        await updatePage.folderInput.clear();
        await updatePage.folderInput.sendKeys('modified');
        expect(await updatePage.folderInput.getAttribute('value')).to.match(/modified/);

        await updatePage.entityNameInput.clear();
        await updatePage.entityNameInput.sendKeys('modified');
        expect(await updatePage.entityNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.createAtInput.clear();
        await updatePage.createAtInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.createAtInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await clear(updatePage.fileSizeInput);
        await updatePage.fileSizeInput.sendKeys('6');
        expect(await updatePage.fileSizeInput.getAttribute('value')).to.eq('6');

        await updatePage.smartUrlInput.clear();
        await updatePage.smartUrlInput.sendKeys('modified');
        expect(await updatePage.smartUrlInput.getAttribute('value')).to.match(/modified/);

        await updatePage.mediumUrlInput.clear();
        await updatePage.mediumUrlInput.sendKeys('modified');
        expect(await updatePage.mediumUrlInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.referenceCountInput);
        await updatePage.referenceCountInput.sendKeys('6');
        expect(await updatePage.referenceCountInput.getAttribute('value')).to.eq('6');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
