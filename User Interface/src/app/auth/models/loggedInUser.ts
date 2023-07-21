/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Role} from './role';

export class LoggedInUser {
    id?: string;
    gid: string;
    firstName: string;
    lastName: string
    username?: string;
    area?: string;
    role?: Role;

    get fullName(): string {
        return this.firstName + ' ' + this.lastName;
    }

    static isAdmin(user: LoggedInUser) {
        return user && user.role === Role.ADMINISTRATOR;
    }

    static isSupervisor(user: LoggedInUser) {
        return user && user.role === Role.SUPERVISOR;
    }

    static isOperator(user: LoggedInUser) {
        return user && user.role === Role.OPERATOR;
    }
}
