export interface UsersRef {
  id: string;
  firstName: string;
  lastName: string;
  role: string;
  status: boolean;
  email: string;
  machines: string[];
  area: string;
  assigned: boolean;
}


export interface UsersInfo {
  id: string;
  fullName: string;
  role: string;
  status: boolean;
  email: string;
  machines: string[];
  area: string;
  assigned: boolean;
}

export interface UserSearch {
  label: string;
  identifier: string;
}
export class CreateUserPayload {
  email: string;
  gid: string;
  role: string;

  firstName: string;
  lastName: string;
}

export class AssignMachine {
  userIds: string[];
  machineIds: string[];
  assign:string;
}

export class userIds{
  identifier:string
}