export interface TemplateMinimal {
    id: string;
    sign: 'ADD' | 'SUB' | 'NONE';
    description: string;
    toConvert: boolean;
    inKg: boolean;
    operation: 'MULTIPLY' | 'DIVIDE';
    operand: number;
    templateType: 'PRODUCTION' | 'REJECTION' | 'TOTAL' | 'EXTRA';
    type: 'CONSTANT' | 'EXPRESSION';

}

export interface ProductSearch {
    identifier: string;
    label: string;
    name: string;
    variants: VariantSearch[];
}

export interface VariantSearch {
    identifier: string;
    label: string;
}

export interface ProductRef {
    id: string;
    number: string;
    area: string;
    description: string;
    weight: number;
}

export interface Template {
    id: number;
    sign: string;
    inKg: boolean;
    description: string;
    toConvert: boolean;
    operation: string;
    operand: number;
    type: string;
    templateType: string;
}

export class NewTemplatePayload {
    id!: number;
    sign: string = '';
    description: string = '';
    toConvert: boolean = false;
    operation: string = '';
    operand!: number;
    formulaType: string = '';
    templateType: string = '';
    area: string = '';
}

export interface Product {
    number: string;
    area: string;
    description: string;
    weight: string;
}

export class NewProductPayload {
    area: string = '';
    description: string = '';
    number: string = '';
    weight!: number;
}


export interface TemplatePayload {
    id: number;
    value: number;
    type: string;
}
