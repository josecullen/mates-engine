import {Component} from '@angular/core';
import {ToolbarComponent, ToolbarConfig} from '../components/toolbar.component';
import {CounterComponent, CounterConfig} from '../components/counter.component';
import {AnswerButtonsComponent} from '../components/answer-buttons.component';
import { MD_CARD_DIRECTIVES } from '@angular2-material/card';
import { MD_BUTTON_DIRECTIVES } from '@angular2-material/button';
import { MD_ICON_DIRECTIVES,MdIconRegistry,MdIcon} from '@angular2-material/icon';

import {TimerService} from '../services/timer.service';
import {Observable} from 'rxjs/Observable';
import {GameProblem} from '../models';
import {MathJaxDirective} from '../directives/mathjax.directive';

@Component({
  selector: 'game-view',
  templateUrl: 'app/views/game-view.component.html',
  styleUrls: ['app/views/game-view.component.css'],
  directives: [MD_CARD_DIRECTIVES, MD_BUTTON_DIRECTIVES, MD_ICON_DIRECTIVES, MD_ICON_DIRECTIVES,
    ToolbarComponent, AnswerButtonsComponent,
    MathJaxDirective
  ]

})
export class GameViewComponent {
  toolbarConfig:ToolbarConfig = new ToolbarConfig();
  gameProblem:GameProblem = new GameProblem("","",["a","b","c","d","e"],[""]);

  constructor(public timerService:TimerService){
    this.toolbarConfig.livesCounter.value = 3;
    this.toolbarConfig.levelCounter.value = 3;
    this.toolbarConfig.scoreCounter.value = 350;
    // this.toolbarConfig.timerCounter.value = 56;

    this.toolbarConfig.timerCounter
        .setObservableValue(this.timerService.gameTimer.timeObservable);
    this.toolbarConfig.extraTimeCounter
        .setObservableValue(this.timerService.extraTimer.timeObservable);

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




  startGame(){

  }

  loadingLevel(){

  }

  playingLevel(){

  }







  processAnswer(answer:string){
    alert(answer);
  }

}
