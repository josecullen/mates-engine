import {Component, Input, Output, EventEmitter,ElementRef,
	ApplicationRef} from '@angular/core';
import {LevelConfig, ExtraScore} from '../../level-config';


@Component({
    selector: 'extra-score',
    templateUrl: 'app/editor/score/extra-score.component.html'
})
export class ExtraScoreComponent {
    @Input() extraScore: ExtraScore;
    @Input() edit: boolean = false;
    @Output() valueChanged: EventEmitter = new EventEmitter();

	constructor(private el:ElementRef, private appRef:ApplicationRef){}

    startEdit() {
        this.edit = !this.edit;
        if(this.edit){
   	        this.appRef.tick();
	        var input:any = this.el.nativeElement.getElementsByTagName("input")[0];
	        input.focus();
        }
    }

    editEnd(name:string, timeThre: number, time: number, extra: number) {
        this.edit = false;
        this.extraScore.name = name;
        this.extraScore.thresholdTime = timeThre;	
        this.extraScore.extraTime = time;
        this.extraScore.extraScore = extra;
        this.valueChanged.emit(this.extraScore);
    }


}