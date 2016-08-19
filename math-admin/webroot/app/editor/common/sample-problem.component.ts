import {
    Component,
    ElementRef,
    Input,
    Output,
    EventEmitter,
    ApplicationRef
} from '@angular/core';
import {MathJaxDirective} from '../../mathjax.directive';
import {LevelConfig, SimpleProblemConfig, ProblemType} from '../../level-config';
import {AdminService} from '../../admin.service';

@Component({
    selector: 'sample-problem',
    template: `
    <div class="row" *ngIf="showProblem" style="background-color: lightgrey; margin-bottom: 10px" >
      <div  class="col-xs-11" align="center">
        <h4 [MathJax]="example" [style.align]="center"></h4>
      </div>
      <div class="col-xs-1">
        <button
          (click)="createProblem()"
          class="btn glyphicon glyphicon-refresh"
          style="margin-top: 5px"></button>
      </div>
    </div>
    `
    ,
    directives: [
        MathJaxDirective
    ]
})
export class SampleProblemComponent{
    @Input() levelConfig:LevelConfig;
    @Input() problemType:ProblemType;
    @Input() showProblem:boolean = false;

    example:string = "";

    constructor(private adminService: AdminService) { }

    toNumber(value) {
        return Number(value);
    }

    toggleShowProblem(){
        this.showProblem = !this.showProblem;
    }

    createProblem(){

      this.adminService.createProblem(this.levelConfig, ProblemType[this.problemType]).subscribe(
        res => {
           this.example = "$"+res.problemExpression+"$";
        },
        err => console.log(err)
      );
    }

}
