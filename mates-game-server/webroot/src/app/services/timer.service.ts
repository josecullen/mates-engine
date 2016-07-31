import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';


@Injectable()
export class TimerService{

  public gameTimer:Timer = new Timer(60, true);
  public problemTimer:Timer = new Timer(0, false);
  public extraTimer:Timer = new Timer(5, true);

}


class Timer{
  private interval;
  private _time;
  private _isActive:boolean;

  public timeObservable:Subject<number> = new Subject<number>();

  constructor(
    public initialValue:number,
    public countdown:boolean
  ){
    this.restart();
  }

  get time(){return this._time;}

  public start(){
    this._isActive = true;
    this.interval = setInterval(() => {
      this.timeObservable.next(this.countdown ? this._time-- : this._time++);
    }, 1000);
  }

  public restart(){
    this._time = this.initialValue;
  }

  public stop(){
    this._isActive = false;
    clearInterval(this.interval);
  }

  public isActive():boolean{
    return this._isActive;
  }

}
