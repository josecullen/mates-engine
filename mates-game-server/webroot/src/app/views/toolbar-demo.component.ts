import {Component} from '@angular/core';
import {ToolbarComponent, ToolbarConfig} from '../components/toolbar.component';
import {CounterComponent, CounterConfig} from '../components/counter.component';
import {TimerService} from '../services/timer.service';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'game-toolbar-demo',
  templateUrl: 'app/views/toolbar-demo.component.html',
  directives: [ToolbarComponent]

})
export class ToolbarDemoComponent {
  toolbarConfig:ToolbarConfig = new ToolbarConfig();
  constructor(public timerService:TimerService){
    this.toolbarConfig.livesCounter.value = 3;
    this.toolbarConfig.levelCounter.value = 3;
    this.toolbarConfig.scoreCounter.value = 350;
    this.toolbarConfig.timerCounter.value = 56;

    this.toolbarConfig.timerCounter
        .setObservableValue(this.timerService.gameTimer.timeObservable);
    this.toolbarConfig.extraTimeCounter
        .setObservableValue(this.timerService.extraTimer.timeObservable);
    //[gameTime]="timerService.gameTimer.time"
    //[extraTime]="timerService.extraTimer.time"

    this.timerService.extraTimer.timeObservable.subscribe(value => {
      this.toolbarConfig.showExtras = value > 0;
      if(value <= 0){
        this.timerService.extraTimer.stop();
        this.timerService.gameTimer.start();
      }
    });

    this.timerService.gameTimer.timeObservable.subscribe(value =>{
      if(this.timerService.extraTimer.isActive()){
        this.timerService.gameTimer.stop();
      }
    });

  }
}
