<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="companyCustomer">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.companyCompanyCustomer.detail.title')">CompanyCustomer</span> {{companyCustomer.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{companyCustomer.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{companyCustomer.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.address')">Address</span>
                    </dt>
                    <dd>
                        <span>{{companyCustomer.address}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.phoneNum')">Phone Num</span>
                    </dt>
                    <dd>
                        <span>{{companyCustomer.phoneNum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.logo')">Logo</span>
                    </dt>
                    <dd>
                        <span>{{companyCustomer.logo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.contact')">Contact</span>
                    </dt>
                    <dd>
                        <span>{{companyCustomer.contact}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.createUserId')">Create User Id</span>
                    </dt>
                    <dd>
                        <span>{{companyCustomer.createUserId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.createTime')">Create Time</span>
                    </dt>
                    <dd>
                        <span v-if="companyCustomer.createTime">{{$d(Date.parse(companyCustomer.createTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyCustomer.parent')">上级</span>
                    </dt>
                    <dd>
                        <div v-if="companyCustomer.parentId">
                            <router-link :to="{name: 'CompanyCustomerView', params: {companyCustomerId: companyCustomer.parentId}}">{{companyCustomer.parentName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="companyCustomer.id" :to="{name: 'CompanyCustomerEdit', params: {companyCustomerId: companyCustomer.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company-customer-details.component.ts">
</script>
