import {Component, Input, Output} from 'angular2/core';
import {Router, RouteParams, ROUTER_DIRECTIVES} from 'angular2/router';
import {LevelConfig, EquationProblemConfig, ProblemType, Stage} from './level-config';
import {Editable} from './td-editable.component';
import {GameConfig} from './game-config';
import {ExtraScoreComponent} from './extra-score.component';
import {AdminService} from './admin.service';
import {HTTP_PROVIDERS}    from 'angular2/http';
import {MathFormComponent} from './math-form-combo.component';
import {SelectOperationComponent} from './select-operations.component';
import {ScoreComponent} from './score.component';
import {ArithLevelComponent} from './arith-level.component';
import {EquationLevelComponent} from './equation-level.component';

@Component({
    selector: 'new-game',
    templateUrl: 'app/new-game.component.html',
    directives: [
        ROUTER_DIRECTIVES, 
        Editable, 
        ExtraScoreComponent, 
        MathFormComponent, 
        SelectOperationComponent,
        ScoreComponent,
        ArithLevelComponent,
        EquationLevelComponent],
    providers: [
        HTTP_PROVIDERS, 
        AdminService]

})
export class NewGameComponent {
    constructor(
        private adminService: AdminService,
        private _routeParams: RouteParams
        ) { }

    gameConfig: GameConfig;
    epics:Array<Array<LevelConfig>>;
    showProblemConfig = 'block';
    showScoreConfig = 'none';
    showHideTables = "Mostrar Configuración de Puntuación";
    showinTable = "scoreConfig";

    ngOnInit() {
        console.log("init NewGameComponent");
        this.gameConfig = JSON.parse(this._routeParams.get('gameConfig'));

        if(!this.gameConfig){
            this.gameConfig = new GameConfig();
            this.gameConfig.stages.push(new Stage());
            this.gameConfig.stages.push(new Stage());
            this.gameConfig.stages[0].levelConfigs[0].problemConfig = new EquationProblemConfig();
            this.gameConfig.stages[1].levelConfigs[0].problemConfig = new EquationProblemConfig();
        }       

        //this.epics = this.splitInEpics(this.gameConfig.levelConfigs);
    }

    splitInEpics(levelConfigs:Array<LevelConfig>):Array<Array<LevelConfig>>{
        let result:Array<Array<LevelConfig>> = new Array();

        let type = levelConfigs[0].problemConfig.getType();
        let from = 0;
        let epic:Array<LevelConfig>;
        for(let i = 1; i < levelConfigs.length; i++){
            let newType:ProblemType = levelConfigs[i].problemConfig.getType();
            if(type != newType){                
                result.push(levelConfigs.slice(from, i));
                from = i;
                type = newType;
            }
            if(type == newType && i == levelConfigs.length - 1){
                result.push(levelConfigs.slice(from, i+1)); 
            }
        }

        return result;
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

    isSimple(levelConfig:LevelConfig){
        return levelConfig.problemConfig.getType() == ProblemType.SIMPLE;
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
