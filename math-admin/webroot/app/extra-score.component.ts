import {Component, Input, Output, EventEmitter} from 'angular2/core';
import {ExtraScore} from './level-config';

@Component({
    selector: 'extra-score'
    template: `
    	<div *ngIf="!edit" (click)="startEdit()">{{extraScore.time}}s. | {{extraScore.extra}}pts.</div>
		<div *ngIf="edit" >
			<input #formTime
				size="6"
				max="999"
				min="0"
				type="number"
				value="{{extraScore.time}}"				
				(keyup.enter)="editEnd(formTime.value, formExtra.value)"
				(keyup.escape)="edit = !edit">
			<input #formExtra
				size="6"
				max="999"
				min="0"
				type="number"
				value="{{extraScore.extra}}"
				(blur)="editEnd(formTime.value, formExtra.value)"
				(keyup.enter)="editEnd(formTime.value, formExtra.value)"
				(keyup.escape)="edit = !edit">
		</div>

    `
})
export class ExtraScoreComponent {
    @Input() extraScore: ExtraScore;
    @Input() edit: boolean = false;
    @Output() valueChanged: EventEmitter = new EventEmitter();

    startEdit() {
        this.edit = !this.edit;
    }

    editEnd(time: number, extra: number) {
        this.edit = false;
        this.extraScore.time = time;
        this.extraScore.extra = extra;
        this.valueChanged.emit(this.extraScore);
    }


}