import {
    Component, 
    ElementRef, 
    Input, 
    Output, 
    EventEmitter,
    ApplicationRef,
    NgSwitch,
    NgSwitchWhen,
    NgSwitchDefault
} from '@angular/core';

@Component({
    selector: 'editable',
    template: `        
        <div *ngIf="!edit" (click)="startEdit(form)">{{value}}</div>
        <div #divForm *ngIf="edit">
            <input #form id="editForm"
                size="6"
                max="999"
                min="0"
                step="{{step}}"
                type="{{type}}"
                value="{{value}}"
                checked="{{value}}"
                (blur)="type == 'checkbox' ? editEnd(form.checked) : editEnd(form.value)"
                (keyup.enter)="type == 'checkbox' ? editEnd(form.checked) : editEnd(form.value)"
                (keyup.escape)="edit = !edit"
            >
        </div>
    `
})
export class Editable {
    @Input() type: string = "number";
    @Input() value: any;
    @Input() edit: boolean = false;
    @Input() step: number = 0;
    @Output() valueChanged: EventEmitter = new EventEmitter();

    el:ElementRef;
    _appRef:ApplicationRef;
    constructor(el:ElementRef, _appRef: ApplicationRef){
        this.el = el;
        this._appRef = _appRef;
    }

    setVisibility(element:any){
        if(element == 'show'){
            return this.edit ? "hidden" : "visible";
        }else{
            return this.edit ? "visible" : "hidden";
        }
    }


    startEdit() {
        this.edit = !this.edit;  
       
        this._appRef.tick();
        var input:any = this.el.nativeElement.children[0].children[0];
        console.log(input);
        input.focus();
        input.autofocus = true;

    }

    saveEl(editForm:any){
        console.log(editForm);
        return this.edit;
    }


    editEnd(editedValue: any) {
        this.edit = false;
        this.value = editedValue;
        this.valueChanged.emit(editedValue);
    }

    toNumber(value) {
        return Number(value);
    }
}

