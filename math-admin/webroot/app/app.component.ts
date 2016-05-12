import {Component} from '@angular/core';
import {
    Router, 
    ROUTER_DIRECTIVES, 
    RouteConfig, 
    ROUTER_PROVIDERS
} from '@angular/router-deprecated';
import {NewGameComponent} from './new-game.component';
import {AdminGameComponent} from './admin-games.component';
import {AdminInstanceComponent} from './admin-instances.component';


@Component({
    selector: 'admin',
    templateUrl: 'app/app.component.html',
    directives: [ROUTER_DIRECTIVES, NewGameComponent],
    providers: [ROUTER_PROVIDERS]
})
@RouteConfig([
    { path: '/new-game/:gameConfig', name: 'NewGame', component: NewGameComponent },
    { path: '/admin-games', name: 'AdminGames', component: AdminGameComponent, useAsDefault: true },
    { path: '/admin-instances', name: 'AdminInstances', component: AdminInstanceComponent }
])
export class AppComponent { 
	constructor(private _router: Router){}

	gotoNewGame(){
		this._router.navigate(['NewGame', {gameConfig: null}]);		
	}

}