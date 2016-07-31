import {Directive, ElementRef, Input} from '@angular/core';
declare var MathJax: any;


@Directive({
    selector: '[MathJax]'
})
export class MathJaxDirective {
    @Input('MathJax') texExpression:string;

    constructor(private el: ElementRef) {

    }

    ngOnChanges() {
      console.log(MathJax);
       this.el.nativeElement.innerHTML = this.texExpression;

       MathJax.Hub.Queue(["Typeset", MathJax.Hub, this.el.nativeElement]);
    }
}
