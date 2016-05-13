import {
    Component, 
    ElementRef, 
    Input, 
    Output, 
    EventEmitter,
    ApplicationRef
} from '@angular/core';
import {MathJaxDirective} from './mathjax.directive';
import {Editable} from './td-editable.component';
import {LevelConfig, SimpleProblemConfig} from './level-config';
import {MathFormComponent} from './math-form-combo.component';
import {SelectOperationComponent} from './select-operations.component';

@Component({
    selector: 'arith-level',
    templateUrl: 'app/arith-level.component.html' ,
    directives: [
        Editable, 
        MathFormComponent,
        SelectOperationComponent,
        MathJaxDirective
    ]
})
export class ArithLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;  
    showProblem:boolean = false;
    addLevel() {
        this.levelConfigs.push(new LevelConfig(new SimpleProblemConfig()));
    } 
    
    toNumber(value) {
        return Number(value);
    }

    toggleShowProblem(){
        this.showProblem = !this.showProblem;
    }

}