import {Component, Input, Output, EventEmitter}	from 'angular2/core';
import {NgClass} from 'angular2/common';



@Component({
	selector: 'select-operations',
	template: `
	  <div class="input-group">
	  	<button class="btn btn-sm icon-plus width-0" 
	  		[ngClass]="{
	    		'icon-plus' : operation.plus, 
	    		'btn-success': operation.plus, 
	    		'icon-plus-outline' : !operation.plus  }"
	    	(click)="operation.plus = !operation.plus; changeValue();"
	  	></button>
		
		<button class="btn btn-sm width-0"  
	    	[ngClass]="{
	    		'icon-minus' : operation.minus, 
	    		'btn-success': operation.minus, 
	    		'icon-minus-outline' : !operation.minus  }"
	    	(click)="operation.minus = !operation.minus;changeValue(); "
	    ></button>

	    <button class="btn btn-sm width-0" 
			[ngClass]="{
	    		'icon-cancel' : operation.multiply, 
	    		'btn-success': operation.multiply, 
	    		'icon-cancel-outline' : !operation.multiply  }"
	    	(click)="operation.multiply = !operation.multiply; changeValue(); "
	    ></button>

	    <button class="btn btn-sm width-0"
			[ngClass]="{
	    		'icon-divide' : operation.divide, 
	    		'btn-success': operation.divide, 
	    		'icon-divide-outline' : !operation.divide  }"
	    	(click)="operation.divide = !operation.divide; changeValue(); "
	    ></button>
	    
	    <button class="btn btn-sm icon-up-open width-0"
	    	[ngClass]="{
	    		'icon-up-open' : operation.pow, 
	    		'btn-success': operation.pow, 
	    		'icon-angle-up' : !operation.pow  }"
	    	(click)="operation.pow = !operation.pow; changeValue(); "
	     ></button>
	 	
	  </div>
	`
})
export class SelectOperationComponent{
	

	@Input() operations:string;
  	@Output("operations") operationsChange:EventEmitter = new EventEmitter();
  	


  	ngOnInit(){  		
  		this.setOperations();
  	}

  	changeValue(event:any){
  		this.operations = "";
  		if(this.operation.plus)
  			this.operations +="+";
  		if(this.operation.multiply)
  			this.operations += "*";
  		if(this.operation.minus)
  			this.operations += "-";
  		if(this.operation.divide)
  			this.operations += "/";
  		if(this.operation.pow)
  			this.operations += "^";

  		console.log(this.operations);
	  	this.operationsChange.next(this.operations);
	}

	setOperations(){
		let operationList:string[] = this.operations.split("");
		this.operation.divide = this.contains(operationList, "/");
		this.operation.multiply = this.contains(operationList, "*");
		this.operation.plus = this.contains(operationList, "+");
		this.operation.minus = this.contains(operationList, "-");
		this.operation.pow = this.contains(operationList, "^");
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
		"minus": true,
		"plus": true,
		"multiply": true,
		"divide": true,
		"pow" : false
	}


}


