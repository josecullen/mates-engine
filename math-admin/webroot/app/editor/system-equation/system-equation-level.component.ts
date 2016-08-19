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
import {LevelConfig, SystemEquationProblemConfig, ProblemType} from '../../level-config';
import {MathFormComponent} from '../../math-form-combo.component';
import {VariableComponent} from '../common/variable.component';
import {SelectOperationComponent} from '../common/select-operations.component';
import {SampleProblemComponent} from '../common/sample-problem.component';

@Component({
    selector: 'system-equation-level',
    templateUrl: 'app/editor/system-equation/system-equation-level.component.html' ,
    directives: [
        Editable,
        MathFormComponent,
        VariableComponent,
        SelectOperationComponent,
        SampleProblemComponent
    ]
})
export class SystemEquationLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;
    showProblem:boolean = false;

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new LogicProblemConfig()));
    }

    toggleShowProblem(){
        this.showProblem = !this.showProblem;
    }

    getProblemType(){
      return ProblemType.SYSTEM_EQUATION;
    }

    toNumber(value) {
        return Number(value);
    }

}
