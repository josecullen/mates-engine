import {Component, Input, Output, EventEmitter} from 'angular2/core';
import {ExtraScore} from './level-config';

@Component({
    selector: 'extra-score',
    template: `
    	<div *ngIf="!edit" (click)="startEdit()">{{extraScore.thresholdTime}}thre. |{{extraScore.extraTime}}s. | {{extraScore.extraScore}}pts.</div>
		<div *ngIf="edit" >
			<input #formTimeThre
				size="6"
				max="999"
				min="0"
				type="number"
				value="{{extraScore.thresholdTime}}"				
				(keyup.enter)="editEnd(formTimeThre.value, formTime.value, formExtra.value)"
				(keyup.escape)="edit = !edit">
			<input #formTime
				size="6"
				max="999"
				min="0"
				type="number"
				value="{{extraScore.extraTime}}"				
				(keyup.enter)="editEnd(formTimeThre.value, formTime.value, formExtra.value)"
				(keyup.escape)="edit = !edit">
			<input #formExtra
				size="6"
				max="999"
				min="0"
				type="number"
				value="{{extraScore.extraScore}}"
				(blur)="editEnd(formTimeThre.value, formTime.value, formExtra.value)"
				(keyup.enter)="editEnd(formTimeThre.value, formTime.value, formExtra.value)"
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

    editEnd(timeThre: number, time: number, extra: number) {
        this.edit = false;
        this.extraScore.thresholdTime = timeThre;	
        this.extraScore.extraTime = time;
        this.extraScore.extraScore = extra;
        this.valueChanged.emit(this.extraScore);
    }


}