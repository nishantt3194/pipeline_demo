/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

export const environment = {
    production: true,
    hmr: false,
    apiUrl: 'https://7gi9kcljv4.execute-api.ap-south-1.amazonaws.com/v1',
    authenticationConfiguration: {
        clientId: "6c401b1a-e95c-46d1-8185-8357bd342ee2",
        authority: "https://login.microsoftonline.com/94c3e67c-9e2d-4800-a6b7-635d97882165",
        redirectUri: window.location.origin  + '/auth/login',
        scopes: ['api://6c401b1a-e95c-46d1-8185-8357bd342ee2/Users.Read']
    }
    // authenticationConfiguration: {
    //     clientId: "0f095333-27f7-4e28-aa2a-4f63d7850eb4",
    //     authority: 'https://login.microsoftonline.com/70ea8e07-3f63-474e-9ba2-a3412e1532ff',
    //     redirectUri: window.location.origin  + '/auth/login',
    //     scopes: ['api://0f095333-27f7-4e28-aa2a-4f63d7850eb4/Users.Read']
    // }
};
