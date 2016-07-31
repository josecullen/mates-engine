import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Headers, RequestOptions} from '@angular/http';
import {Observable}     from 'rxjs/Observable';

import {GameInstance} from '../models';

@Injectable()
export class MatesServices{
  private pathGameInstance: string = "http://localhost:10000/arithmetic/v1/instance/one?instanceId=576322f4af21d21adc4adabf&gameId=5762d2b63e68b5130b5fcd03";

  constructor(private http: Http) { }

  getGameInstance():any {
    return this.http.get(this.pathGameInstance)
    	.map(res => {
			     console.log(res);
			        return res;
      });

  }



}
