import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);

import Router from 'vue-router';
import UserLayout from '../layouts/UserLayout.vue';
import BasicLayout from '../layouts/BasicLayout.vue';
import { bxAnaalyse } from '@/core/icons';
import { PageView, RouteView } from '@/layouts';
import { businessRoute } from '@/business/business.route';

const UserLogin = () => import('../views/user/Login.vue');
const UserRegister = () => import('../views/user/Register.vue');
const UserRegisterResult = () => import('../views/user/RegisterResult.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
const JhiTrackerComponent = () => import('../admin/tracker/tracker.vue');
/* tslint:disable */
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here
const originalPush = Router.prototype.push;
// @ts-ignore
Router.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch(err => err);
};
Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: BasicLayout,
      redirect: '/dashboard/analysis',
      meta: { authorities: ['ROLE_ADMIN'] },
      children: [
        // dashboard
        {
          path: '/dashboard',
          name: 'dashboard',
          redirect: '/dashboard/analysis',
          component: RouteView,
          meta: { title: '仪表盘', keepAlive: true, icon: bxAnaalyse, authorities: ['ROLE_ADMIN'], permission: ['dashboard'] },
          children: [
            {
              path: '/dashboard/analysis',
              name: 'Analysis',
              component: () => import('@/views/dashboard/Analysis.vue'),
              meta: { title: '分析页', keepAlive: false, permission: ['dashboard'], authorities: ['ROLE_ADMIN'] }
            }          ]
        }      ]
    },
    businessRoute,
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
,
    {
      path: '/admin/jhi-tracker',
      name: 'JhiTrackerComponent',
      component: JhiTrackerComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
    ,{
      path: '/user',
      component: UserLayout,
      redirect: '/user/login',
      children: [
        {
          path: 'login',
          name: 'login',
          component: UserLogin
        },
        {
          path: 'register',
          name: 'register',
          component: UserRegister
        },
        {
          path: 'register-result',
          name: 'registerResult',
          component: UserRegisterResult
        },
        {
          path: 'recover',
          name: 'recover',
          component: undefined
        }
      ]
    },
    {
      path: '/404',
      component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404.vue')
    },
    {
      path: '*',
      redirect: '/404'
    }
  ]
});
