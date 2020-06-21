/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../../page-objects/navbar-page';
import ViewPermissionComponentsPage, { ViewPermissionDeleteDialog } from './view-permission.page-object';
import ViewPermissionUpdatePage from './view-permission-update.page-object';
import ViewPermissionDetailsPage from './view-permission-details.page-object';

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

describe('ViewPermission e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ViewPermissionUpdatePage;
  let detailsPage: ViewPermissionDetailsPage;
  let listPage: ViewPermissionComponentsPage;
  let deleteDialog: ViewPermissionDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load ViewPermissions', async () => {
    await navBarPage.getEntityPage('view-permission');
    listPage = new ViewPermissionComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });

  describe('Create flow', () => {
    it('should load create ViewPermission page', async () => {
      await listPage.createButton.click();
      updatePage = new ViewPermissionUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/jhiAntVueApp.systemViewPermission.home.createOrEditLabel/);
    });

    it('should create and save ViewPermissions', async () => {
      await updatePage.textInput.sendKeys('text');
      expect(await updatePage.textInput.getAttribute('value')).to.match(/text/);

      await updatePage.i18nInput.sendKeys('i18n');
      expect(await updatePage.i18nInput.getAttribute('value')).to.match(/i18n/);

      const selectedGroup = await updatePage.groupInput.isSelected();
      if (selectedGroup) {
        await updatePage.groupInput.click();
        expect(await updatePage.groupInput.isSelected()).to.be.false;
      } else {
        await updatePage.groupInput.click();
        expect(await updatePage.groupInput.isSelected()).to.be.true;
      }

      await updatePage.linkInput.sendKeys('link');
      expect(await updatePage.linkInput.getAttribute('value')).to.match(/link/);

      await updatePage.externalLinkInput.sendKeys('externalLink');
      expect(await updatePage.externalLinkInput.getAttribute('value')).to.match(/externalLink/);

      await selectLastOption(updatePage.targetSelect);

      await updatePage.iconInput.sendKeys('icon');
      expect(await updatePage.iconInput.getAttribute('value')).to.match(/icon/);

      const selectedDisabled = await updatePage.disabledInput.isSelected();
      if (selectedDisabled) {
        await updatePage.disabledInput.click();
        expect(await updatePage.disabledInput.isSelected()).to.be.false;
      } else {
        await updatePage.disabledInput.click();
        expect(await updatePage.disabledInput.isSelected()).to.be.true;
      }

      const selectedHide = await updatePage.hideInput.isSelected();
      if (selectedHide) {
        await updatePage.hideInput.click();
        expect(await updatePage.hideInput.isSelected()).to.be.false;
      } else {
        await updatePage.hideInput.click();
        expect(await updatePage.hideInput.isSelected()).to.be.true;
      }

      const selectedHideInBreadcrumb = await updatePage.hideInBreadcrumbInput.isSelected();
      if (selectedHideInBreadcrumb) {
        await updatePage.hideInBreadcrumbInput.click();
        expect(await updatePage.hideInBreadcrumbInput.isSelected()).to.be.false;
      } else {
        await updatePage.hideInBreadcrumbInput.click();
        expect(await updatePage.hideInBreadcrumbInput.isSelected()).to.be.true;
      }

      const selectedShortcut = await updatePage.shortcutInput.isSelected();
      if (selectedShortcut) {
        await updatePage.shortcutInput.click();
        expect(await updatePage.shortcutInput.isSelected()).to.be.false;
      } else {
        await updatePage.shortcutInput.click();
        expect(await updatePage.shortcutInput.isSelected()).to.be.true;
      }

      const selectedShortcutRoot = await updatePage.shortcutRootInput.isSelected();
      if (selectedShortcutRoot) {
        await updatePage.shortcutRootInput.click();
        expect(await updatePage.shortcutRootInput.isSelected()).to.be.false;
      } else {
        await updatePage.shortcutRootInput.click();
        expect(await updatePage.shortcutRootInput.isSelected()).to.be.true;
      }

      const selectedReuse = await updatePage.reuseInput.isSelected();
      if (selectedReuse) {
        await updatePage.reuseInput.click();
        expect(await updatePage.reuseInput.isSelected()).to.be.false;
      } else {
        await updatePage.reuseInput.click();
        expect(await updatePage.reuseInput.isSelected()).to.be.true;
      }

      await updatePage.codeInput.sendKeys('code');
      expect(await updatePage.codeInput.getAttribute('value')).to.match(/code/);

      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);

      await selectLastOption(updatePage.typeSelect);

      await updatePage.orderInput.sendKeys('5');
      expect(await updatePage.orderInput.getAttribute('value')).to.eq('5');

      await updatePage.apiPermissionCodesInput.sendKeys('apiPermissionCodes');
      expect(await updatePage.apiPermissionCodesInput.getAttribute('value')).to.match(/apiPermissionCodes/);

      // await  selectLastOption(updatePage.apiPermissionsSelect);
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

        deleteDialog = new ViewPermissionDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/jhiAntVueApp.systemViewPermission.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details ViewPermission page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ViewPermissionDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit ViewPermission page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.textInput.clear();
        await updatePage.textInput.sendKeys('modified');
        expect(await updatePage.textInput.getAttribute('value')).to.match(/modified/);

        await updatePage.i18nInput.clear();
        await updatePage.i18nInput.sendKeys('modified');
        expect(await updatePage.i18nInput.getAttribute('value')).to.match(/modified/);

        const selectedGroup = await updatePage.groupInput.isSelected();
        if (selectedGroup) {
          await updatePage.groupInput.click();
          expect(await updatePage.groupInput.isSelected()).to.be.false;
        } else {
          await updatePage.groupInput.click();
          expect(await updatePage.groupInput.isSelected()).to.be.true;
        }

        await updatePage.linkInput.clear();
        await updatePage.linkInput.sendKeys('modified');
        expect(await updatePage.linkInput.getAttribute('value')).to.match(/modified/);

        await updatePage.externalLinkInput.clear();
        await updatePage.externalLinkInput.sendKeys('modified');
        expect(await updatePage.externalLinkInput.getAttribute('value')).to.match(/modified/);

        await updatePage.iconInput.clear();
        await updatePage.iconInput.sendKeys('modified');
        expect(await updatePage.iconInput.getAttribute('value')).to.match(/modified/);

        const selectedDisabled = await updatePage.disabledInput.isSelected();
        if (selectedDisabled) {
          await updatePage.disabledInput.click();
          expect(await updatePage.disabledInput.isSelected()).to.be.false;
        } else {
          await updatePage.disabledInput.click();
          expect(await updatePage.disabledInput.isSelected()).to.be.true;
        }

        const selectedHide = await updatePage.hideInput.isSelected();
        if (selectedHide) {
          await updatePage.hideInput.click();
          expect(await updatePage.hideInput.isSelected()).to.be.false;
        } else {
          await updatePage.hideInput.click();
          expect(await updatePage.hideInput.isSelected()).to.be.true;
        }

        const selectedHideInBreadcrumb = await updatePage.hideInBreadcrumbInput.isSelected();
        if (selectedHideInBreadcrumb) {
          await updatePage.hideInBreadcrumbInput.click();
          expect(await updatePage.hideInBreadcrumbInput.isSelected()).to.be.false;
        } else {
          await updatePage.hideInBreadcrumbInput.click();
          expect(await updatePage.hideInBreadcrumbInput.isSelected()).to.be.true;
        }

        const selectedShortcut = await updatePage.shortcutInput.isSelected();
        if (selectedShortcut) {
          await updatePage.shortcutInput.click();
          expect(await updatePage.shortcutInput.isSelected()).to.be.false;
        } else {
          await updatePage.shortcutInput.click();
          expect(await updatePage.shortcutInput.isSelected()).to.be.true;
        }

        const selectedShortcutRoot = await updatePage.shortcutRootInput.isSelected();
        if (selectedShortcutRoot) {
          await updatePage.shortcutRootInput.click();
          expect(await updatePage.shortcutRootInput.isSelected()).to.be.false;
        } else {
          await updatePage.shortcutRootInput.click();
          expect(await updatePage.shortcutRootInput.isSelected()).to.be.true;
        }

        const selectedReuse = await updatePage.reuseInput.isSelected();
        if (selectedReuse) {
          await updatePage.reuseInput.click();
          expect(await updatePage.reuseInput.isSelected()).to.be.false;
        } else {
          await updatePage.reuseInput.click();
          expect(await updatePage.reuseInput.isSelected()).to.be.true;
        }

        await updatePage.codeInput.clear();
        await updatePage.codeInput.sendKeys('modified');
        expect(await updatePage.codeInput.getAttribute('value')).to.match(/modified/);

        await updatePage.descriptionInput.clear();
        await updatePage.descriptionInput.sendKeys('modified');
        expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.orderInput);
        await updatePage.orderInput.sendKeys('6');
        expect(await updatePage.orderInput.getAttribute('value')).to.eq('6');

        await updatePage.apiPermissionCodesInput.clear();
        await updatePage.apiPermissionCodesInput.sendKeys('modified');
        expect(await updatePage.apiPermissionCodesInput.getAttribute('value')).to.match(/modified/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
