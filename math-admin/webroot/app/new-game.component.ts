import {Component, Input, Output} from 'angular2/core';
import {Router, RouteParams, ROUTER_DIRECTIVES} from 'angular2/router';
import {LevelConfig} from './level-config';
import {Editable} from './td-editable.component';
import {GameConfig} from './game-config';
import {ExtraScoreComponent} from './extra-score.component';
import {AdminService} from './admin.service';
import {HTTP_PROVIDERS}    from 'angular2/http';

@Component({
    selector: 'new-game',
    templateUrl: 'app/new-game.component.html',
    directives: [ROUTER_DIRECTIVES, Editable, ExtraScoreComponent],
    providers: [HTTP_PROVIDERS, AdminService]

})
export class NewGameComponent {
    constructor(
        private adminService: AdminService,
        private _routeParams: RouteParams
        ) { }

    gameConfig: GameConfig;
    showProblemConfig = 'block';
    showScoreConfig = 'none';
    showHideTables = "Mostrar Configuración de Puntuación";
    showinTable = "scoreConfig";

    ngOnInit() {
        console.log("init NewGameComponent");
        this.gameConfig = JSON.parse(this._routeParams.get('gameConfig'));

        if(!this.gameConfig){
            this.gameConfig = new GameConfig();
            this.gameConfig.levelConfigs.push(new LevelConfig());
            this.gameConfig.levelConfigs.push(new LevelConfig());
        }       
    }

    changeShowTable() {
        if (this.showProblemConfig == 'block') {
            this.showHideTables = "Mostrar Configuración de Problema";
            this.showProblemConfig = 'none';
            this.showScoreConfig = 'block';
        } else {
            this.showHideTables = "Mostrar Configuración de Puntuación";
            this.showScoreConfig = 'none';
            this.showProblemConfig = 'block';
        }
    }

    addLevel() {
        this.gameConfig.levelConfigs.push(new LevelConfig());
    }

    printProblem() {
        return JSON.stringify(this.gameConfig);
    }

    printGame() {
        console.log(this.gameConfig);
    }

    toNumber(value) {
        return Number(value);
    }

    getSaveMessage(){
       return this.gameConfig._id ? "Actualizar" : "¡Crear Juego!";        
    }

    saveOrUpdateGame() {
        if(!this.gameConfig){
            this.adminService.saveGame(this.gameConfig).subscribe(
                res => console.log(res),
                error => console.log(error));
        }else{
            this.adminService.updateGameConfig(this.gameConfig).subscribe(
                res => console.log(res),
                error => console.log(error));
        }
        
    }

}
