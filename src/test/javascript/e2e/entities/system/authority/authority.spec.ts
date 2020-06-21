/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import AuthorityComponentsPage, { AuthorityDeleteDialog } from './authority.page-object';
import AuthorityUpdatePage from './authority-update.page-object';
import AuthorityDetailsPage from './authority-details.page-object';

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

describe('Authority e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: AuthorityUpdatePage;
  let detailsPage: AuthorityDetailsPage;
  let listPage: AuthorityComponentsPage;
  let deleteDialog: AuthorityDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load Authorities', async () => {
    await navBarPage.getEntityPage('authority');
    listPage = new AuthorityComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create Authority page', async () => {
      await listPage.createButton.click();
      updatePage = new AuthorityUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.systemAuthority.home.createOrEditLabel/);
    });

    it('should create and save Authorities', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.codeInput.sendKeys('code');
      expect(await updatePage.codeInput.getAttribute('value')).to.match(/code/);

      await updatePage.infoInput.sendKeys('info');
      expect(await updatePage.infoInput.getAttribute('value')).to.match(/info/);

      await updatePage.orderInput.sendKeys('5');
      expect(await updatePage.orderInput.getAttribute('value')).to.eq('5');

      const selectedDisplay = await updatePage.displayInput.isSelected();
      if (selectedDisplay) {
        await updatePage.displayInput.click();
        expect(await updatePage.displayInput.isSelected()).to.be.false;
      } else {
        await updatePage.displayInput.click();
        expect(await updatePage.displayInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.usersSelect);
      // await  selectLastOption(updatePage.viewPermissionSelect);
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

        deleteDialog = new AuthorityDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.systemAuthority.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Authority page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new AuthorityDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Authority page, fetch data and update', async () => {
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

        await updatePage.infoInput.clear();
        await updatePage.infoInput.sendKeys('modified');
        expect(await updatePage.infoInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.orderInput);
        await updatePage.orderInput.sendKeys('6');
        expect(await updatePage.orderInput.getAttribute('value')).to.eq('6');

        const selectedDisplay = await updatePage.displayInput.isSelected();
        if (selectedDisplay) {
          await updatePage.displayInput.click();
          expect(await updatePage.displayInput.isSelected()).to.be.false;
        } else {
          await updatePage.displayInput.click();
          expect(await updatePage.displayInput.isSelected()).to.be.true;
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
