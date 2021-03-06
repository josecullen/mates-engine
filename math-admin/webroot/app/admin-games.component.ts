import {Component} 		from '@angular/core';
import {AdminService} 	from './admin.service';
import {HTTP_PROVIDERS} from '@angular/http';
import {Router, RouteParams, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {GameConfig, InstanceGameData} from './game-config';

@Component({
    selector: 'admin-games',
    templateUrl: 'app/admin-games.component.html',
    directives: [ROUTER_DIRECTIVES],
    providers: [HTTP_PROVIDERS, AdminService]
})
export class AdminGameComponent {
    gameConfigs:Array<GameConfig>;

    constructor(
        private adminService: AdminService,
        private _router: Router) { }

	ngOnInit() {
        console.log("init AdminGameComponent");
		this.getGames();        
    }

    getGames(){
        this.adminService.getAllGames().subscribe(
            res => {
                this.gameConfigs = res;

                this.gameConfigs.forEach(function(item){
                    item.instanceGameDatas = Array<InstanceGameData>;
                });                

                this.getInstanceGameData();                
                
            },
            error => console.log(error)
        );
    }

    getInstanceGameData(){
        this.adminService.getAllInstanceGameData().subscribe(
            instanceRes => {
                this.populateGameConfigsData(instanceRes);               
            },
            instanceError =>  console.log(instanceError)
        );
    }

    populateGameConfigsData(instanceRes:InstanceGameData[]){
        for(var i:number = 0; i < instanceRes.length; i++){
            var instanceGameData:InstanceGameData = instanceRes[i];
            this.gameConfigs.forEach(function(gameConfig){
                if(instanceGameData.gameId == gameConfig._id){
                    gameConfig.instanceGameDatas.push(instanceGameData);
                    return;
                }                                
            });
        }        
    }

    removeGame(index:number){
        var gameConfig:GameConfig = this.gameConfigs[index];
        console.log(gameConfig._id);
        this.adminService.removeGame(gameConfig._id).subscribe(
            res => this.gameConfigs.splice(index,1),
            error => console.log(error));
    }

    createInstance(gameConfig:GameConfig){
        gameConfig.instanceGameDatas.push(new InstanceGameData(gameConfig._id,gameConfig.name+" "+(gameConfig.instanceGameDatas.length+1))
    }

    edit(gameConfig:GameConfig){
        this._router.navigate(['NewGame',{gameConfig:JSON.stringify(gameConfig)}]);
    }

    saveInstance(instanceGameData:InstanceGameData){
        if(!instanceGameData._id){
            this.adminService.saveInstanceGameData(instanceGameData).subscribe(
                res => {
                    console.log(res);
                },
                error => console.log(error));
            
            window.location.reload();

        }else{        
            this.adminService.updateInstanceGameData(instanceGameData).subscribe(
                res => console.log(res),
                error => console.log(error));
        }
    }

    removeInstance(indexGame:number, indexInstance:number){
        var instance:InstanceGameData = this.gameConfigs[indexGame].instanceGameDatas[indexInstance];

        if(!instance._id || instance._id.length == 0 ){
            this.gameConfigs[indexGame].instanceGameDatas.splice(indexInstance,1);
        }else{
            console.log(instance._id);
            this.adminService.removeInstanceGameData(instance._id).subscribe(res => {
                console.log(res);
                this.gameConfigs[indexGame].instanceGameDatas.splice(indexInstance,1);
            }, error => console.log(error));
        }
    }

    

    

}


