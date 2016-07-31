import { Component} from '@angular/core';
import {HTTP_PROVIDERS} from '@angular/http';
import { MD_CARD_DIRECTIVES } from '@angular2-material/card';
import { MD_BUTTON_DIRECTIVES } from '@angular2-material/button';
import { MD_ICON_DIRECTIVES,MdIconRegistry,MdIcon} from '@angular2-material/icon';

import {ToolbarComponent} from './components/toolbar.component';
import {ToolbarDemoComponent} from './views/toolbar-demo.component';
import {GameViewComponent} from './views/game-view.component';
import {TimerService} from './services/timer.service';
import {MatesServices} from './services/mates.services';

import * as models from './models';

@Component({
  moduleId: module.id,
  selector: 'game-mates-app',
  templateUrl: 'game-mates.component.html',
  styleUrls: ['game-mates.component.css'],
  directives: [MD_CARD_DIRECTIVES, MD_BUTTON_DIRECTIVES, MD_ICON_DIRECTIVES, MD_ICON_DIRECTIVES,
    ToolbarComponent, ToolbarDemoComponent, GameViewComponent],
  providers: [HTTP_PROVIDERS, TimerService, MatesServices
  ],
  viewProviders: [MdIconRegistry]

})
export class GameMatesAppComponent {
  title = 'game-mates works!';
  gameInstance:models.GameInstance;
  constructor(
    private mdIconRegistry:MdIconRegistry,
    private timerService:TimerService,

    private matesServices:MatesServices
    ){

    mdIconRegistry
      .addSvgIcon('thumb-up', '/game-mates/icon/assets/thumbup-icon.svg')
      .addSvgIconSetInNamespace('core', '/game-mates/icon/assets/core-icon-set.svg')
      .registerFontClassAlias('fontawesome', 'fa');

    this.matesServices.getGameInstance().subscribe(res => console.log(res));
//    timerService.gameTimer.start();

  };



}
