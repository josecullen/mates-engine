import {
  beforeEachProviders,
  describe,
  expect,
  it,
  inject
} from '@angular/core/testing';
import { GameMatesAppComponent } from '../app/game-mates.component';

beforeEachProviders(() => [GameMatesAppComponent]);

describe('App: GameMates', () => {
  it('should create the app',
      inject([GameMatesAppComponent], (app: GameMatesAppComponent) => {
    expect(app).toBeTruthy();
  }));

  it('should have as title \'game-mates works!\'',
      inject([GameMatesAppComponent], (app: GameMatesAppComponent) => {
    expect(app.title).toEqual('game-mates works!');
  }));
});
