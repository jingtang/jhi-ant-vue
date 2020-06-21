<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="businessType">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.companyBusinessType.detail.title')">BusinessType</span> {{businessType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyBusinessType.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{businessType.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyBusinessType.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{businessType.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyBusinessType.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{businessType.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.companyBusinessType.icon')">Icon</span>
                    </dt>
                    <dd>
                        <span>{{businessType.icon}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="businessType.id" :to="{name: 'BusinessTypeEdit', params: {businessTypeId: businessType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./business-type-details.component.ts">
</script>
