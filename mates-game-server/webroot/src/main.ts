import { bootstrap } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { GameMatesAppComponent, environment } from './app/';
import 'rxjs/Rx';

if (environment.production) {
  enableProdMode();
}

bootstrap(GameMatesAppComponent);
