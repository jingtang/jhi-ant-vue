/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';
import moment, { Moment } from 'moment';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import CommonQueryService from '@/business/commonQuery/common-query/common-query.service';
import { CommonQuery } from '@/shared/model/commonQuery/common-query.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('CommonQuery Service', () => {
    let service: CommonQueryService;
    let elemDefault;
    let currentDate: Moment;
    beforeEach(() => {
      service = new CommonQueryService();
      currentDate = moment(Date.now());

      elemDefault = new CommonQuery(0, 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            lastModifiedTime: currentDate
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).subscribe(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a CommonQuery', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastModifiedTime: currentDate
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastModifiedTime: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a CommonQuery', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            lastModifiedTime: currentDate
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastModifiedTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of CommonQuery', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            lastModifiedTime: currentDate
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastModifiedTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).subscribe(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a CommonQuery', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).subscribe(res => {
          expect(res.data.ok).toBeTruthy();
        });
      });
    });
  });
});
