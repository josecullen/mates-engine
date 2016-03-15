import {LevelConfig} from './level-config';

export class GameConfig {
	public _id:any;
	public instanceGameDatas:Array<InstanceGameData>;
    constructor(
        public name: string = "Juego Nuevo",
        public levelConfigs: Array<LevelConfig> = [new LevelConfig()];
        ) { }

}

export class InstanceGameData{
    public _id:any;

    constructor(
        public gameId:string,
        public instanceName:string
        ){}
}