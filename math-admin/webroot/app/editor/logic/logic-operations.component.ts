import {Component, Input, Output, EventEmitter}	from 'angular2/core';
import {NgClass} from 'angular2/common';
import {MathJaxDirective} from '../../mathjax.directive';


@Component({
	selector: 'select-logic-operations',
	templateUrl: 'app/editor/logic/logic-operations.component.html',
	directives: [MathJaxDirective]
})
export class SelectLogicOperationComponent{
	

	@Input() operations:string;
  	@Output("operations") operationsChange:EventEmitter = new EventEmitter();


  	ngOnInit(){  		
  		this.setOperations();
  	}

  	changeValue(event:any){
  		this.operations = "";
  		if(this.operation.and)
  			this.operations +="and|";
  		if(this.operation.or)
  			this.operations += "or|";
  		if(this.operation.then)
  			this.operations += "then|";
  		if(this.operation.eq)
  			this.operations += "eq|";

  		console.log(this.operations);
	  	this.operationsChange.next(this.operations);
	}

	setOperations(){
		let operationList:string[] = this.operations.split("|");
		this.operation.and = this.contains(operationList, "and");
		this.operation.or = this.contains(operationList, "or");
		this.operation.then = this.contains(operationList, "then");
		this.operation.eq = this.contains(operationList, "eq");
		
	}

	private contains(list:string[], value:string):boolean{
		for(var i = 0; i < list.length; i++){
			if(list[i] == value){
				return true
			}
		}

		return false;
	}

	operation = {
		"and": true,
		"or": true,
		"then": true,
		"eq": true
	}


}


