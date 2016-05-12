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

@Component({
    selector: 'module-level',
    templateUrl: 'app/editor/module/module-level.component.html' ,
    directives: [
        Editable, 
        VariableComponent
    ]
})
export class ModuleLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;  

    addLevel() {
        this.levelConfigs.push(new LevelConfig(new ModuleProblemConfig()));
    } 
    
    toNumber(value) {
        return Number(value);
    }
    
}