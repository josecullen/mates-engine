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
import {SampleProblemComponent} from '../common/sample-problem.component';

@Component({
    selector: 'logic-level',
    templateUrl: 'app/editor/logic/logic-level.component.html' ,
    directives: [
        Editable,
        MathFormComponent,
        SelectLogicOperationComponent,
        SampleProblemComponent
    ]
})
export class LogicLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;
    showProblem:boolean = false;

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new LogicProblemConfig()));
    }

    toggleShowProblem(){
        this.showProblem = !this.showProblem;
    }

    getProblemType(){
      return ProblemType.LOGIC;
    }


}
