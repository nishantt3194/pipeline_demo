/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {CoreConfig} from '@core/types';

/**
 * Default App Config
 * 
 * ? TIP:
 *
 * Change app config based on your preferences.
 * You can also change them on each component basis. i.e `app/main/pages/authentication/auth-login-v1/auth-login-v1.component.ts`
 *
 * ! IMPORTANT: If the enableLocalStorage option is true then make sure you clear the browser local storage(https://developers.google.com/web/tools/chrome-devtools/storage/localstorage#delete).
 *  ! Otherwise, it will not take the below config changes and use stored config from local storage.
 *
 */

// prettier-ignore
export const coreConfig: CoreConfig = {
    app: {
        appName: 'BECTON DICKINSON',                                        // App Name
        appTitle: 'BECTON DICKINSON',                                         // App Title
        sAppTitleFirstHalf: 'BECTON',
        sAppTitleSecondHalf: 'DICKINSON',
        appLogoImage: 'assets/images/logo/logo.svg',
        appLogoExtended: 'assets/images/logo/logo.svg', // App Logo
        appLanguage: 'en',                                           // App Default Language (en, fr, de, pt etc..)
    },
    layout: {
        skin: 'bordered',                        // default, dark, bordered, semi-dark
        type: 'vertical',                       // vertical, horizontal
        animation: 'fadeIn',                     // fadeInLeft, zoomIn , fadeIn, none
        menu: {
            hidden: false,           // Boolean: true, false
            collapsed: false,           // Boolean: true, false
        },
        // ? For horizontal menu, navbar testType will work for navMenu testType
        navbar: {
            hidden: false,           // Boolean: true, false
            type: 'd-none',  // navbar-static-top, fixed-top, floating-nav, d-none
            background: 'navbar-light',  // navbar-light. navbar-dark
            customBackgroundColor: false,            // Boolean: true, false
            backgroundColor: 'bg-primary'               // BS color i.e bg-primary, bg-success
        },
        footer: {
            hidden: false,           // Boolean: true, false
            type: 'd-none', // footer-static, footer-sticky, d-none
            background: 'footer-light',  // footer-light. footer-dark
            customBackgroundColor: false,           // Boolean: true, false
            backgroundColor: ''               // BS color i.e bg-primary, bg-success
        },
        enableLocalStorage: false,
        customizer: false,                       // Boolean: true, false (Enable theme customizer)
        scrollTop: false,                       // Boolean: true, false (Enable scroll to top button)
        buyNow: false                        // Boolean: true, false (Set false in real project, For demo purpose only)
    }
}
