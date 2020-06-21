<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="companyUser">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.companyCompanyUser.detail.title')">CompanyUser</span> {{companyUser.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyUser.user')">关联账户</span>
                    </dt>
                    <dd>
                        {{companyUser.userFirstName}}
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyCompanyUser.company')">所属单位</span>
                    </dt>
                    <dd>
                        <div v-if="companyUser.companyId">
                            <router-link :to="{name: 'CompanyCustomerView', params: {companyCustomerId: companyUser.companyId}}">{{companyUser.companyName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="companyUser.id" :to="{name: 'CompanyUserEdit', params: {companyUserId: companyUser.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company-user-details.component.ts">
</script>
