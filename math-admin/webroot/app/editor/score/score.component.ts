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
import {ExtraScoreComponent} from './extra-score.component';
import {LevelConfig} from '../../level-config';


@Component({
    selector: 'score',
    templateUrl: 'app/editor/score/score.component.html',
    directives: [
        Editable, 
        ExtraScoreComponent]
})
export class ScoreComponent{
    @Input() levelConfigs:Array<LevelConfig>;
}