/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import CommonQueryItemService from '@/business/commonQuery/common-query-item/common-query-item.service';
import { CommonQueryItem } from '@/shared/model/commonQuery/common-query-item.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('CommonQueryItem Service', () => {
    let service: CommonQueryItemService;
    let elemDefault;
    beforeEach(() => {
      service = new CommonQueryItemService();

      elemDefault = new CommonQueryItem(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).subscribe(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a CommonQueryItem', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a CommonQueryItem', async () => {
        const returnedFromService = Object.assign(
          {
            prefix: 'BBBBBB',
            fieldName: 'BBBBBB',
            fieldType: 'BBBBBB',
            operator: 'BBBBBB',
            value: 'BBBBBB',
            suffix: 'BBBBBB',
            order: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of CommonQueryItem', async () => {
        const returnedFromService = Object.assign(
          {
            prefix: 'BBBBBB',
            fieldName: 'BBBBBB',
            fieldType: 'BBBBBB',
            operator: 'BBBBBB',
            value: 'BBBBBB',
            suffix: 'BBBBBB',
            order: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).subscribe(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a CommonQueryItem', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).subscribe(res => {
          expect(res.data.ok).toBeTruthy();
        });
      });
    });
  });
});
