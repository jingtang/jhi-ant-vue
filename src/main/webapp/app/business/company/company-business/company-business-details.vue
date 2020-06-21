<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="companyBusiness">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.companyCompanyBusiness.detail.title')">CompanyBusiness</span> {{companyBusiness.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyBusiness.status')">Status</span>
                    </dt>
                    <dd>
                        <span v-text="$t('jhiAntVueApp.CompanyBusinessStatus.' + companyBusiness.status)">{{companyBusiness.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyBusiness.expirationTime')">Expiration Time</span>
                    </dt>
                    <dd>
                        <span v-if="companyBusiness.expirationTime">{{$d(Date.parse(companyBusiness.expirationTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyBusiness.startTime')">Start Time</span>
                    </dt>
                    <dd>
                        <span v-if="companyBusiness.startTime">{{$d(Date.parse(companyBusiness.startTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyBusiness.operateUserId')">Operate User Id</span>
                    </dt>
                    <dd>
                        <span>{{companyBusiness.operateUserId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyBusiness.groupId')">Group Id</span>
                    </dt>
                    <dd>
                        <span>{{companyBusiness.groupId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyBusiness.businessType')">业务类型</span>
                    </dt>
                    <dd>
                        <div v-if="companyBusiness.businessTypeId">
                            <router-link :to="{name: 'BusinessTypeView', params: {businessTypeId: companyBusiness.businessTypeId}}">{{companyBusiness.businessTypeName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyBusiness.company')">公司</span>
                    </dt>
                    <dd>
                        <div v-if="companyBusiness.companyId">
                            <router-link :to="{name: 'CompanyCustomerView', params: {companyCustomerId: companyBusiness.companyId}}">{{companyBusiness.companyName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="companyBusiness.id" :to="{name: 'CompanyBusinessEdit', params: {companyBusinessId: companyBusiness.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company-business-details.component.ts">
</script>
