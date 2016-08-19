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
import {LevelConfig, SimpleProblemConfig, ProblemType} from './level-config';
import {MathFormComponent} from './math-form-combo.component';
import {SelectOperationComponent} from './select-operations.component';
import {AdminService} from './admin.service';
import {SampleProblemComponent} from './editor/common/sample-problem.component';

@Component({
    selector: 'arith-level',
    templateUrl: 'app/arith-level.component.html' ,
    directives: [
        Editable,
        MathFormComponent,
        SelectOperationComponent,
        MathJaxDirective,
        SampleProblemComponent
    ]
})
export class ArithLevelComponent{
    @Input() levelConfigs:Array<LevelConfig>;
    showProblem:boolean = false;

    constructor(private adminService: AdminService) { }


    addLevel() {
        this.levelConfigs.push(new LevelConfig(new SimpleProblemConfig()));
    }

    toNumber(value) {
        return Number(value);
    }

    toggleShowProblem(){
        this.showProblem = !this.showProblem;
    }

    getProblemType(){
      return ProblemType.SIMPLE;
    }

    createProblem(levelIndex:number){

      this.adminService.createProblem(this.levelConfigs[levelIndex], ProblemType[ProblemType.SIMPLE]).subscribe(
        res => {
           console.log(res);
           this.examples[levelIndex] = "$"+res.solvedExpression+"$";
        },
        err => console.log(err)
      );
    }

}
