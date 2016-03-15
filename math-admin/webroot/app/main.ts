import {bootstrap}    from 'angular2/platform/browser'
import {ROUTER_PROVIDERS} from 'angular2/router';
import {AppComponent} from './app.component';
import {AdminGameComponent} from './admin-games.component';
import {provide}           from 'angular2/core';
import {LocationStrategy,
        HashLocationStrategy} from 'angular2/router';
import 'rxjs/Rx';

bootstrap(AppComponent, [ROUTER_PROVIDERS,provide(LocationStrategy,{useClass: HashLocationStrategy}) ]);
