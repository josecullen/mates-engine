import {Component, Input, Output, EventEmitter}	from 'angular2/core';

@Component({
	selector: 'select-operations',
	template: `
	  <div class="input-group">
	    <button class="btn btn-success btn-sm glyphicon glyphicon-asterisk"></button>
	    <button class="btn btn-success btn-sm glyphicon glyphicon-minus"></button>
	    <button class="btn btn-success btn-sm glyphicon glyphicon-plus"></button>
	    <button class="btn btn-success btn-sm glyphicon glyphicon-superscript"></button>
	    	   
	  </div>
	`
})
export class SelectOperationComponent{
	
	@Input() operations:string = this.forms[0];
  	@Output("operations") operationsChange:EventEmitter = new EventEmitter();
  	


  	ngOnInit(){  		
  		this.setOperations();
  	}

  	changeValue(event:any){
	  this.operationsChange.next(event.target.value);
	}

	setOperations(){
		let operationList:string[] = this.operations.split(",");


	}

	operation = {
		"+": true,
		"-": true,
		"*": true,
		"/": true
	}


}



class Operations{

}
