<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="leave">
                <h2 class="jh-entity-heading"><span v-text="$t('jhiAntVueApp.workflowLeave.detail.title')">Leave</span> {{leave.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowLeave.createTime')">Create Time</span>
                    </dt>
                    <dd>
                        <span v-if="leave.createTime">{{$d(Date.parse(leave.createTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowLeave.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{leave.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowLeave.days')">Days</span>
                    </dt>
                    <dd>
                        <span>{{leave.days}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowLeave.startTime')">Start Time</span>
                    </dt>
                    <dd>
                        <span v-if="leave.startTime">{{$d(Date.parse(leave.startTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowLeave.endTime')">End Time</span>
                    </dt>
                    <dd>
                        <span v-if="leave.endTime">{{$d(Date.parse(leave.endTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowLeave.reason')">Reason</span>
                    </dt>
                    <dd>
                        <span>{{leave.reason}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhiAntVueApp.workflowLeave.creator')">申请人</span>
                    </dt>
                    <dd>
                        {{leave.creatorLogin}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="leave.id" :to="{name: 'LeaveEdit', params: {leaveId: leave.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./leave-details.component.ts">
</script>
