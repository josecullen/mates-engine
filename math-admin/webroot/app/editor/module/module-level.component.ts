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
import {LevelConfig, ModuleProblemConfig, ProblemType} from '../../level-config';
import {VariableComponent} from '../common/variable.component';
import {SampleProblemComponent} from '../common/sample-problem.component';

@Component({
    selector: 'module-level',
    templateUrl: 'app/editor/module/module-level.component.html' ,
    directives: [
        Editable,
        VariableComponent,
        SampleProblemComponent
    ]
})
export class ModuleLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;
    showProblem:boolean = false;

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new ModuleProblemConfig()));
    }

    toNumber(value) {
        return Number(value);
    }

    toggleShowProblem(){
        this.showProblem = !this.showProblem;
    }

    getProblemType(){
      return ProblemType.MODULE;
    }

}
