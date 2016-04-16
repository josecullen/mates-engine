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
import {LevelConfig, SimpleProblemConfig} from './level-config';
import {MathFormComponent} from './math-form-combo.component';
import {SelectOperationComponent} from './select-operations.component';

@Component({
    selector: 'arith-level',
    templateUrl: 'app/arith-level.component.html' ,
    directives: [
        Editable, 
        ExtraScoreComponent,
        MathFormComponent,
        SelectOperationComponent]
})
export class ArithLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;  

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new SimpleProblemConfig()));
    } 
    
}