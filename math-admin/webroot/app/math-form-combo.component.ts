import {Component, Input, Output, EventEmitter}	from '@angular/core';

@Component({
	selector: 'math-form-combo',
	template: `
		<select [(ngModel)]="form" (change)="changeValue($event)">
			<option *ngFor="#aForm of forms" value="{{aForm}}">{{aForm}} </option>
		</select>

	`
})
export class MathFormComponent{
	forms:Array<string> = [
		"a + b",
		"a + (b + c)",
		"(a + b) + c",
		"(a + b) + (c + d)",
		"( ((a + b) + (c + d)) + e )",
		"( e + ((a + b) + (c + d) ) )",
		"((a + b) + (c + d)) + (f + g)"
		];


	@Input() form:string = this.forms[0];
  	@Output("form") formChange:EventEmitter = new EventEmitter();


  	changeValue(event:any){
	  this.formChange.next(event.target.value);
	}

}

