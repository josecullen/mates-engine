export class GameInstance{
  constructor(
    private _instanceId:string = "",
    private _gameId:string = "",
    private _levels:Array<GameLevel> = new Array()
  ){}

  get instanceId(){ return this.instanceId}
  get gameId(){ return this.gameId}
  get levels(){ return this.levels}
}

export class GameLevel{
  constructor(
    private _gameProblems:Array<GameProblem>,
	  private _scoreConfig:ScoreConfig
  ){}
}

export class GameProblem{
  constructor(
    private _problemExpression:string ,
  	private _solvedExpression:string,
  	private _answerOptions:string[],
  	private _correctAnswer:string[]
  ){}

  get answerOptions(){return this._answerOptions;}
}

export class ScoreConfig{
  constructor(
    private _baseScore:number,
    private _preCount:number,
    private _withTime:boolean,
    private _extras:Array<ExtraScore>
  ){}
}

export class ExtraScore{
  constructor(
    private _name:string,
    private _extraTime:number,
    private _extraScore:number,
    private _thresholdTime:number
  ){}

}



export class GameStatus{
  constructor(
    public stage:number = 0,
    public level:number = 0,
    public problem:number = 0,
    public score:number = 0,
    public lives:number = 3
  ){

  }
}
