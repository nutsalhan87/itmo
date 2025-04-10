import { AbstractControl, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { DateTime } from "luxon";

export enum VehicleType {
    HELICOPTER = "HELICOPTER",
    DRONE = "DRONE",
    SHIP = "SHIP",
    BICYCLE = "BICYCLE",
    CHOPPER = "CHOPPER",
}

export const displayVehicleType: { [key in VehicleType]: string } = {
    [VehicleType.HELICOPTER]: "Вертолет",
    [VehicleType.DRONE]: "Дрон",
    [VehicleType.SHIP]: "Корабль",
    [VehicleType.BICYCLE]: "Велосипед",
    [VehicleType.CHOPPER]: "Мотоцикл"
}

export interface Coordinates {
    x: number,
    y: number,
}

export interface Vehicle {
    id: number,
    name: string,
    coordinates: Coordinates,
    creationDate: Date,
    enginePower: number,
    numberOfWheels: number,
    distanceTravelled: number,
    type: VehicleType,
}

export type VehicleFlattened = Omit<Vehicle, "coordinates"> & Coordinates;
export const vehicleFlattenedKeys: (keyof VehicleFlattened)[] = [ // handtype
    "id",
    "name",
    "creationDate",
    "enginePower",
    "numberOfWheels",
    "distanceTravelled",
    "type",
    "x",
    "y"
];

export type Sort = {
    key: keyof VehicleFlattened,
    isAsc: boolean,
};

export enum FilterCmpOp {
    GT = "Больше",
    GE = "Больше или равно",
    LT = "Меньше",
    LE = "Меньше или равно",
}

export enum FilterEqOp {
    EQ = "Равно",
    NE = "Не равно",
}

export enum FilterInOp {
    CONTAINS = "Содержит",
}

export const filterOpOperators: { [key in (FilterCmpOp | FilterEqOp | FilterInOp)]: string } = {
    [FilterCmpOp.GT]: ">",
    [FilterCmpOp.GE]: ">=",
    [FilterCmpOp.LT]: "<",
    [FilterCmpOp.LE]: "<=",
    [FilterEqOp.EQ]: "=",
    [FilterEqOp.NE]: "!=",
    [FilterInOp.CONTAINS]: "~",
};

export type Filter<T extends VehicleFlattened[keyof VehicleFlattened]> = { // handtype
    op: string extends T
    ? (FilterEqOp | FilterInOp)
    : (VehicleType extends T
        ? FilterEqOp
        : (FilterCmpOp | FilterEqOp)),
    value: T,
};

export type FilterMap = { [Property in keyof VehicleFlattened]: Filter<VehicleFlattened[Property]>[] };

export type VehicleFlattenedOmitFrozen = Omit<VehicleFlattened, "id" | "creationDate">; // handtype
export const vehicleFlattenedOmitIdKeys: (keyof VehicleFlattenedOmitFrozen)[] = [ // handtype
    "name",
    "enginePower",
    "numberOfWheels",
    "distanceTravelled",
    "type",
    "x",
    "y"
];

const DATE_FORMAT: string = "yyyy-LL-dd";

export function dateFromString(value: string): Date | null {
    let parsed = DateTime.fromFormat(value, DATE_FORMAT);
    return parsed.isValid ? parsed.toJSDate() : null;
}

export function stringFromDate(value: Date): string {
    return DateTime.fromJSDate(value).toFormat(DATE_FORMAT);
}

function notEmptyValidator(control: AbstractControl<string>): ValidationErrors {
    return control.value.trim().length == 0 ? { empty: "Value must be not empty" } : {};
}

export function integerValidator(control: AbstractControl<string>): ValidationErrors {
    const INT_MAX = 2147483647;
    const INT_MIN = -2147483648;
    let parsed = parseFloat(control.value);
    if (!Number.isInteger(parsed)) {
        return { nonInteger: "Value must be an integer" };
    }
    if (parsed > INT_MAX || parsed < INT_MIN) {
        return { intOverflow: "Value overflowed int boundaries" };
    }
    return {};
}

function longValidator(control: AbstractControl<string>): ValidationErrors {
    const LONG_MAX = 9223372036854775807;
    const LONG_MIN = -9223372036854775808;
    let parsed = parseFloat(control.value);
    if (!Number.isInteger(parsed)) {
        return { nonInteger: "Value must be an integer" };
    }
    if (parsed > LONG_MAX || parsed < LONG_MIN) {
        return { longOverflow: "Value overflowed long boundaries" };
    }
    return {};
}

function dateValidator(control: AbstractControl<string>): ValidationErrors {
    if (dateFromString(control.value) == null) {
        return { formattingError: `Date is not according to format ${DATE_FORMAT} or is not valid` };
    }
    return {};
}

type InputType = "number" | "text" | ["select", string[]]; // handtype
export const typeConstructorsAndValidators: { [Property in keyof VehicleFlattened]: { // handtype
    header: string,
    constructor: () => VehicleFlattened[Property] extends Date ? string : VehicleFlattened[Property],
    validators: ValidatorFn[],
    inputType: InputType,
    helperMessage: string,
} } = {
    id: {
        header: "ID",
        constructor: () => Number(1),
        validators: [Validators.required, Validators.min(1), integerValidator],
        inputType: "number",
        helperMessage: `Поле не может быть пустым
      Значение должно быть натуральным числом`,
    },
    name: {
        header: "Имя",
        constructor: () => String(""),
        validators: [Validators.required, notEmptyValidator],
        inputType: "text",
        helperMessage: `Строка не может быть пустой`,
    },
    creationDate: {
        header: "Дата создания",
        constructor: () => DateTime.fromObject({ year: 2038, month: 1, day: 19 }).toFormat(DATE_FORMAT),
        validators: [Validators.required, dateValidator],
        inputType: "text",
        helperMessage: `Значение должно соответствовать формату ${DATE_FORMAT}
        Дата должна быть возможной`,
    },
    enginePower: {
        header: "Мощность",
        constructor: () => Number(1),
        validators: [Validators.required, Validators.min(1)],
        inputType: "number",
        helperMessage: `Поле не может быть пустым
      Значение поля должно быть больше 0`,
    },
    numberOfWheels: {
        header: "Число колес",
        constructor: () => Number(1),
        validators: [Validators.required, Validators.min(1), longValidator],
        inputType: "number",
        helperMessage: `Поле не может быть пустым
      Значение должно быть натуральным числом`,
    },
    distanceTravelled: {
        header: "Пробег",
        constructor: () => Number(1),
        validators: [Validators.required, Validators.min(1)],
        inputType: "number",
        helperMessage: `Поле не может быть пустым
      Значение поля должно быть больше 0`,
    },
    type: {
        header: "Тип",
        constructor: () => VehicleType.BICYCLE,
        validators: [],
        inputType: ["select", Object.values(VehicleType)],
        helperMessage: `Выбирайте то, что хотите!`,
    },
    x: {
        header: "x",
        constructor: () => Number(0),
        validators: [Validators.required, longValidator],
        inputType: "number",
        helperMessage: `Поле не может быть пустым
      Значение должно быть целым числом`,
    },
    y: {
        header: "y",
        constructor: () => Number(0),
        validators: [Validators.required, Validators.max(126), longValidator],
        inputType: "number",
        helperMessage: `Поле не может быть пустым
      Значение должно быть целым числом
      Значение не должно быть больше 126`,
    }
};