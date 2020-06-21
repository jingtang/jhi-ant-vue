/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import CommonTableRelationshipService from '@/business/modelConfig/common-table-relationship/common-table-relationship.service';
import {
  CommonTableRelationship,
  RelationshipType,
  SourceType,
  FixedType
} from '@/shared/model/modelConfig/common-table-relationship.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('CommonTableRelationship Service', () => {
    let service: CommonTableRelationshipService;
    let elemDefault;
    beforeEach(() => {
      service = new CommonTableRelationshipService();

      elemDefault = new CommonTableRelationship(
        0,
        'AAAAAAA',
        RelationshipType.ONE_TO_MANY,
        SourceType.ENTITY,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        FixedType.LEFT,
        false,
        false,
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).subscribe(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a CommonTableRelationship', async () => {
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

      it('should update a CommonTableRelationship', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            relationshipType: 'BBBBBB',
            sourceType: 'BBBBBB',
            otherEntityField: 'BBBBBB',
            otherEntityName: 'BBBBBB',
            relationshipName: 'BBBBBB',
            otherEntityRelationshipName: 'BBBBBB',
            columnWidth: 1,
            order: 1,
            fixed: 'BBBBBB',
            editInList: true,
            enableFilter: true,
            hideInList: true,
            hideInForm: true,
            fontColor: 'BBBBBB',
            backgroundColor: 'BBBBBB',
            help: 'BBBBBB',
            ownerSide: true,
            dataName: 'BBBBBB',
            webComponentType: 'BBBBBB',
            otherEntityIsTree: true,
            showInFilterTree: true,
            dataDictionaryCode: 'BBBBBB',
            clientReadOnly: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).subscribe(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of CommonTableRelationship', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            relationshipType: 'BBBBBB',
            sourceType: 'BBBBBB',
            otherEntityField: 'BBBBBB',
            otherEntityName: 'BBBBBB',
            relationshipName: 'BBBBBB',
            otherEntityRelationshipName: 'BBBBBB',
            columnWidth: 1,
            order: 1,
            fixed: 'BBBBBB',
            editInList: true,
            enableFilter: true,
            hideInList: true,
            hideInForm: true,
            fontColor: 'BBBBBB',
            backgroundColor: 'BBBBBB',
            help: 'BBBBBB',
            ownerSide: true,
            dataName: 'BBBBBB',
            webComponentType: 'BBBBBB',
            otherEntityIsTree: true,
            showInFilterTree: true,
            dataDictionaryCode: 'BBBBBB',
            clientReadOnly: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).subscribe(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a CommonTableRelationship', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).subscribe(res => {
          expect(res.data.ok).toBeTruthy();
        });
      });
    });
  });
});
