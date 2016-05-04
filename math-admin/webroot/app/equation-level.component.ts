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
import {Editable} from './td-editable.component';
import {ExtraScoreComponent} from './extra-score.component';
import {LevelConfig, EquationProblemConfig} from './level-config';
import {MathFormComponent} from './math-form-combo.component';
import {SelectOperationComponent} from './select-operations.component';
import {VariableComponent} from './editor/common/variable.component';

@Component({
    selector: 'equation-level',
    templateUrl: 'app/equation-level.component.html' ,
    directives: [
        Editable, 
        ExtraScoreComponent,
        MathFormComponent,
        SelectOperationComponent,
        VariableComponent]
})
export class EquationLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;   
    
    printProblem() {
        return JSON.stringify(this.levelConfigs);
    }

    toNumber(value) {
        return Number(value);
    }

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new EquationProblemConfig()));
    }

}