/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
    production: false,
    hmr: false,
    // apiUrl: 'https://7gi9kcljv4.execute-api.ap-south-1.amazonaws.com/v1',
    apiUrl: 'http://localhost:8001/v1',
    // authenticationConfiguration: {
    //     clientId: "6c401b1a-e95c-46d1-8185-8357bd342ee2",
    //     authority: "https://login.microsoftonline.com/94c3e67c-9e2d-4800-a6b7-635d97882165",
    //     redirectUri: window.location.origin + '/auth/login',
    //     scopes: ['api://6c401b1a-e95c-46d1-8185-8357bd342ee2/Users.Read']
    // }
    authenticationConfiguration: {
        clientId: "0f095333-27f7-4e28-aa2a-4f63d7850eb4",
        authority: 'https://login.microsoftonline.com/70ea8e07-3f63-474e-9ba2-a3412e1532ff',
        redirectUri: window.location.origin,
        scopes: ['api://0f095333-27f7-4e28-aa2a-4f63d7850eb4/Users.Read']
    }
};

/*
 * For easier debugging in development mode, you can import the following fileh9ow 
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.


// export const environment = {
//     production: false,
//     api: 'http://localhost:8080/',
//     auth: {
//         clientId: '167d0eeb-d62d-4069-8593-4e20c4b3b05a',
//         authority: 'https://login.microsoftonline.com/294c1ad7-c9aa-4b80-b617-28f4bcce1545',
//         loginSuccess: window.location.origin + '/auth/login',
//         loginFailed: window.location.origin + '/auth/login?failed',
//         logout: window.location.origin + '/auth/login?logout',
//         scopes: ['api://167d0eeb-d62d-4069-8593-4e20c4b3b05a/Users.Read'],
//     },
// };
