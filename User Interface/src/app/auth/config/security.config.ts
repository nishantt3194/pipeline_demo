import {environment} from "../../../environments/environment";
import {BrowserCacheLocation} from "@azure/msal-browser";

export const AuthenticationConfgiruation = { // MSAL Configuration
    auth: {
        clientId: environment.authenticationConfiguration.clientId,
        authority: environment.authenticationConfiguration.authority,
        redirectUri: environment.authenticationConfiguration.redirectUri,
    },
    cache: {
        cacheLocation: BrowserCacheLocation.LocalStorage,
        storeAuthStateInCookie: true, // set to true for IE 11
    },
    system: {
        loggerOptions: {
            loggerCallback: () => {
            },
            piiLoggingEnabled: false
        }
    }
}