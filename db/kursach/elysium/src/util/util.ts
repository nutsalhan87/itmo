import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export const emptyValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    return control.value.trim().length == 0 ? { emptyValue: { value: control.value } } : null;
};