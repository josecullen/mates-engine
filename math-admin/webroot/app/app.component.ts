import {Component} from 'angular2/core';
import {Router, RouteParams, ROUTER_DIRECTIVES, RouteConfig} from 'angular2/router';
import {NewGameComponent} from './new-game.component';
import {AdminGameComponent} from './admin-games.component';
import {AdminInstanceComponent} from './admin-instances.component';


@Component({
    selector: 'admin',
    templateUrl: 'app/app.component.html',
    directives: [ROUTER_DIRECTIVES, NewGameComponent]
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