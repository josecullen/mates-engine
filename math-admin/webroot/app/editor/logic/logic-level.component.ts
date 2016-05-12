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
import {Editable} from '../../td-editable.component';
import {LevelConfig, LogicProblemConfig, ProblemType} from '../../level-config';
import {MathFormComponent} from '../../math-form-combo.component';
import {SelectLogicOperationComponent} from "./logic-operations.component";

@Component({
    selector: 'logic-level',
    templateUrl: 'app/editor/logic/logic-level.component.html' ,
    directives: [
        Editable, 
        MathFormComponent,
        SelectLogicOperationComponent
    ]
})
export class LogicLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;  

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new LogicProblemConfig()));
    } 
    
}