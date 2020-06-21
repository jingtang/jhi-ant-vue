/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';
import moment, { Moment } from 'moment';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import CompanyBusinessService from '@/business/company/company-business/company-business.service';
import { CompanyBusiness, CompanyBusinessStatus } from '@/shared/model/company/company-business.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('CompanyBusiness Service', () => {
    let service: CompanyBusinessService;
    let elemDefault;
    let currentDate: Moment;
    beforeEach(() => {
      service = new CompanyBusinessService();
      currentDate = moment(Date.now());

      elemDefault = new CompanyBusiness(0, CompanyBusinessStatus.OPEN, currentDate, currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            expirationTime: currentDate,
            startTime: currentDate
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).subscribe(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a CompanyBusiness', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            expirationTime: currentDate,
            startTime: currentDate
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            expirationTime: currentDate,
            startTime: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a CompanyBusiness', async () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            expirationTime: currentDate,
            startTime: currentDate,
            operateUserId: 1,
            groupId: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            expirationTime: currentDate,
            startTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of CompanyBusiness', async () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            expirationTime: currentDate,
            startTime: currentDate,
            operateUserId: 1,
            groupId: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            expirationTime: currentDate,
            startTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).subscribe(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a CompanyBusiness', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).subscribe(res => {
          expect(res.data.ok).toBeTruthy();
        });
      });
    });
  });
});
