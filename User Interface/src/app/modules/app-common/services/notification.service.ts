/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Injectable} from '@angular/core';

import * as SockJS from 'sockjs-client';
import {IMessage, Stomp} from "@stomp/stompjs";
import {environment} from "../../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class NotificationService {
    private _client = Stomp.over(function () {
        return new SockJS(environment.apiUrl + "wss");
    });

    constructor() {
    }

    init() {
        const connected: boolean = this._client.connected;
        if (!connected) {
            this._client.connect({}, (): any => {
            })
            this._client.activate();
        }
    }

    subscribe(topic: string, callback: (response: IMessage) => void): void {
        const connected: boolean = this._client.connected;

        if (connected) {
            this._subscribeToTopic(topic, callback);
            return;
        }

        this._client.connect({}, (): any => {
            this._subscribeToTopic(topic, callback);
        })
    }

    unsubscribe(topic: string): void {
        this._client.unsubscribe(topic);
    }

    private _subscribeToTopic(topic: string,
                              callback: (response?: IMessage) => void): void {
        this._client.subscribe(topic, (response?) => {
            callback(response);
        })
    }

}
