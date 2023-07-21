export interface AccessLogRef {
    email: string;
    name: string;
    timestamp: string;
    result: string;
}

export interface OperationLog {
    timestamp: string;
    operation: string;
    doneBy: string;
    doneById: string;
    association: string;
    associationDesc: string;
    changes: string[];
}
