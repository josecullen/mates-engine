import {
	Component,
	Input,
	Output,
	EventEmitter,
	ElementRef,
	ApplicationRef
	} from 'angular2/core';
import {VariableConfig} from '../../level-config';

@Component({
	selector: 'variable',
    templateUrl: 'app/editor/common/variable.component.html' 
})
export class VariableComponent{
	@Input()  variableConfig:VariableConfig;
    @Input() edit: boolean = false;
	@Output("variableConfig") variableEdited:EventEmitter = new EventEmitter();


	constructor(private el:ElementRef, private appRef:ApplicationRef){}

    toggleEdit() {
        this.edit = !this.edit;
        if(this.edit){
   	        this.appRef.tick();
	        var input:any = this.el.nativeElement.getElementsByTagName("input")[0];
	        input.focus();
        }
    }

	editEnd(min:number, max:number, div:number, sign:number){
		this.variableConfig = new VariableConfig(min,max,sign,div);
		this.variableEdited.emit(this.variableConfig);
		this.toggleEdit();
	}

}