import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Headers, RequestOptions} from '@angular/http';
import {Observable}     from 'rxjs/Observable';
import {GameConfig, InstanceGameData} from './game-config';
import {ProblemConfigm, LevelConfig} from './level-config';

@Injectable()
export class AdminService {
    constructor(private http: Http) { }

    saveGame(gameConfig: GameConfig) {
        let body = JSON.stringify({ gameConfig });
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post("/arithmetic/v2/game", body, options)
            .map(res => res.data )
            .catch(this.handleError);
    }

    removeGame(gameId){
        return this.http.delete("/arithmetic/v2/game"+"?_id="+gameId)
            .map(res => res.json())
            .catch(this.handleError);
    }

    getAllGames(){
        return this.http.get("/arithmetic/v2/games")
            .map(res => {
                return <GameConfig[]>res.json();
            })
            .catch(this.handleError);
    }

    updateGameConfig(gameConfig: GameConfig){
        let body = JSON.stringify({ gameConfig });
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.put("/arithmetic/v2/game", JSON.stringify(gameConfig), options)
            .map(res => JSON.parse(res._body))
            .catch(this.handleError);
    }


    getAllInstanceGameData(){
        return this.http.get("/arithmetic/v2/instanceGameData")
            .map(res => {
                return <InstanceGameData[]>res.json();
            })
            .catch(this.handleError);
    }

    saveInstanceGameData(instanceGameData:InstanceGameData){
        let body = JSON.stringify({ instanceGameData });
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post("/arithmetic/v2/instanceGameData", JSON.stringify(instanceGameData), options)
            .map(res => JSON.parse(res._body))
            .catch(this.handleError);
    }

    updateInstanceGameData(instanceGameData:InstanceGameData){
        let body = JSON.stringify({ instanceGameData });
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.put("/arithmetic/v2/instanceGameData", JSON.stringify(instanceGameData), options)
            .map(res => JSON.parse(res._body))
            .catch(this.handleError);
    }

    removeInstanceGameData(instanceId){
        return this.http.delete("/arithmetic/v2/instanceGameData"+"?_id="+instanceId)
            .map(res => {
                console.log(res);
                return res.json();
            })
            .catch(this.handleError);
    }

    createProblem(levelConfig:LevelConfig, problemType:string){
      let body = JSON.stringify({ levelConfig: levelConfig, problemType : problemType });
      let headers = new Headers({ 'Content-Type': 'application/json' });
      let options = new RequestOptions({ headers: headers });

      return this.http.post("arithmetic/v2/problem", body, options)
        .map(res => {
          console.log(res);
          return JSON.parse(res._body);
        })
        .catch(this.handleError);
    }

    handleError(err){
      console.log(err);
    }

}
