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
} from 'angular2/core';
import {Editable} from '../../td-editable.component';
import {ExtraScoreComponent} from '../../extra-score.component';
import {LevelConfig, LogicProblemConfig, ProblemType} from '../../level-config';
import {MathFormComponent} from '../../math-form-combo.component';
import {SelectLogicOperationComponent} from "./logic-operations.component";

@Component({
    selector: 'logic-level',
    templateUrl: 'app/editor/logic/logic-level.component.html' ,
    directives: [
        Editable, 
        ExtraScoreComponent,
        MathFormComponent,
        SelectLogicOperationComponent]
})
export class LogicLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;  

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new LogicProblemConfig()));
    } 
    
}