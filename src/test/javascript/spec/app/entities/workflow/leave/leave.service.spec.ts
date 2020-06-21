/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';
import moment, { Moment } from 'moment';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import LeaveService from '@/business/workflow/leave/leave.service';
import { Leave } from '@/shared/model/workflow/leave.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Leave Service', () => {
    let service: LeaveService;
    let elemDefault;
    let currentDate: Moment;
    beforeEach(() => {
      service = new LeaveService();
      currentDate = moment(Date.now());

      elemDefault = new Leave(0, currentDate, 'AAAAAAA', 0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createTime: currentDate,
            startTime: currentDate,
            endTime: currentDate
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).subscribe(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Leave', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createTime: currentDate,
            startTime: currentDate,
            endTime: currentDate
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createTime: currentDate,
            startTime: currentDate,
            endTime: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Leave', async () => {
        const returnedFromService = Object.assign(
          {
            createTime: currentDate,
            name: 'BBBBBB',
            days: 1,
            startTime: currentDate,
            endTime: currentDate,
            reason: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createTime: currentDate,
            startTime: currentDate,
            endTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Leave', async () => {
        const returnedFromService = Object.assign(
          {
            createTime: currentDate,
            name: 'BBBBBB',
            days: 1,
            startTime: currentDate,
            endTime: currentDate,
            reason: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createTime: currentDate,
            startTime: currentDate,
            endTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).subscribe(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Leave', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).subscribe(res => {
          expect(res.data.ok).toBeTruthy();
        });
      });
    });
  });
});
