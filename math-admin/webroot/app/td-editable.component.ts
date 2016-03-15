import {Component, Input, Output, EventEmitter} from 'angular2/core';

@Component({
    selector: 'editable',
    template: `
	<div *ngIf="!edit" (click)="startEdit()">{{value}}</div>
	<div *ngIf="edit" >
		<input #form
			size="6"
			max="999"
			min="0"
			type="{{type}}"
			value="{{value}}"
			checked="{{value}}"
			(blur)="type == 'checkbox' ? editEnd(form.checked) : editEnd(form.value)"
			(keyup.enter)="type == 'checkbox' ? editEnd(form.checked) : editEnd(form.value)"
			(keyup.escape)="edit = !edit">
	</div>
    `

})
export class Editable {
    @Input() type: string = "number";
    @Input() value: any;
    @Input() edit: boolean;
    @Output() valueChanged: EventEmitter = new EventEmitter();
    edit: boolean = false;

    startEdit() {
        this.edit = !this.edit;
    }



    editEnd(editedValue: any) {
        this.edit = false;
        this.value = editedValue;
        this.valueChanged.emit(editedValue);
    }
}

