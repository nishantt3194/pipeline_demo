/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import 'hammerjs';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {TranslateModule} from '@ngx-translate/core';
import {ToastrModule} from 'ngx-toastr';

import {MsalBroadcastService, MsalGuard, MsalInterceptor, MsalModule, MsalRedirectComponent} from "@azure/msal-angular";
import {InteractionType, PublicClientApplication} from "@azure/msal-browser";


import {CoreModule} from '@core/core.module';
import {CoreCommonModule} from '@core/common.module';
import {CoreSidebarModule, CoreThemeCustomizerModule} from '@core/components';

import {coreConfig} from 'app/app-config';
import {AppComponent} from 'app/app.component';
import {LayoutModule} from 'app/layout/layout.module';
import {DatePipe} from '@angular/common';
import {ErrorInterceptor, PostInterceptor,} from './auth/helpers';

import {environment} from '../environments/environment';
import {CreditTrimPipe} from "./modules/app-common/pipes/credit-trim.pipe";
import {ApiInterceptor} from "./auth/helpers/api.interceptor";
import {AppCommonModule} from "./modules/app-common/app-common.module";
import {AuthenticationConfgiruation} from "./auth/config/security.config";


const appRoutes: Routes = [
    {
        path: 'app',
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./main/pages/pages.module').then((m) => m.PagesModule),
    },
    {
        path: '',
        redirectTo: '/dashboard/overview',
        pathMatch: 'full',
    },
    {
        path: 'auth/login',
        redirectTo: '/dashboard/overview',
        pathMatch: 'full',
    },
    {
        path: 'dashboard',
        canActivate: [MsalGuard],
        loadChildren: () =>
        import('./modules/dashboard/dashboard.module').then((m) => m.DashboardModule),

    },
    {
        path: 'products',
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./modules/products/products.module').then((m) => m.ProductsModule),
    },
    {
        path: 'machines',
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./modules/machines/machines.module').then((m) => m.MachinesModule),
    },
    {
        path: 'utilization' ,
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./modules/utilization/utilization.module').then((m) => m.UtilizationModule),
    },
    {
        path: 'entries',
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./modules/entries/entries.module').then((m) => m.EntriesModule),
    },
    {
        path: 'logs',
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./modules/logs/logs.module').then((m) => m.LogsModule),
    },
    {
        path: 'users',
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./modules/users/users.module').then((m) => m.UsersModule),
    },
   
    {
        path: 'settings',
        canActivate: [MsalGuard],
        loadChildren: () =>
            import('./modules/settings/settings.module').then((m) => m.SettingsModule),
    },
    {
        path: '**',
        redirectTo: '/pages/error', // Error 404 - Page not found
    },
];

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        
        RouterModule.forRoot(appRoutes, {
            scrollPositionRestoration: 'enabled', // Add options right here
            relativeLinkResolution: 'legacy',
            onSameUrlNavigation: 'reload'
        }),

        MsalModule.forRoot(
            new PublicClientApplication(AuthenticationConfgiruation),
            {
                interactionType: InteractionType.Redirect, // MSAL Guard Configuration
            },
            {
                interactionType: InteractionType.Redirect, // MSAL Interceptor Configuration
                protectedResourceMap: new Map<string, Array<string>>([
                    [environment.apiUrl, environment.authenticationConfiguration.scopes]
                ])
            }),

        TranslateModule.forRoot(),

        //NgBootstrap
        NgbModule,
        ToastrModule.forRoot(),

        // Core modules
        CoreModule.forRoot(coreConfig),
        AppCommonModule,
        CoreCommonModule,
        CoreSidebarModule,
        CoreThemeCustomizerModule,
        // App modules
        LayoutModule,
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: MsalInterceptor,
            multi: true
        },
        MsalGuard,
        MsalBroadcastService,
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: PostInterceptor, multi: true},
        // {provide: HTTP_INTERCEPTORS, useClass: ApiInterceptor, multi: true},
        DatePipe, CreditTrimPipe
    ],
    bootstrap: [AppComponent, MsalRedirectComponent],
})
export class AppModule {
}
