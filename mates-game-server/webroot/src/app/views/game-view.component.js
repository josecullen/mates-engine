"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var toolbar_component_1 = require('../components/toolbar.component');
var answer_buttons_component_1 = require('../components/answer-buttons.component');
var card_1 = require('@angular2-material/card');
var button_1 = require('@angular2-material/button');
var icon_1 = require('@angular2-material/icon');
var timer_service_1 = require('../services/timer.service');
var models_1 = require('../models');
var mathjax_directive_1 = require('../directives/mathjax.directive');
var GameViewComponent = (function () {
    function GameViewComponent(timerService) {
        var _this = this;
        this.timerService = timerService;
        this.toolbarConfig = new toolbar_component_1.ToolbarConfig();
        this.gameProblem = new models_1.GameProblem("", "", ["a", "b", "c", "d", "e"], [""]);
        this.toolbarConfig.livesCounter.value = 3;
        this.toolbarConfig.levelCounter.value = 3;
        this.toolbarConfig.scoreCounter.value = 350;
        this.toolbarConfig.timerCounter.value = 56;
        this.toolbarConfig.timerCounter
            .setObservableValue(this.timerService.gameTimer.timeObservable);
        this.toolbarConfig.extraTimeCounter
            .setObservableValue(this.timerService.extraTimer.timeObservable);
        this.timerService.extraTimer.timeObservable.subscribe(function (value) {
            _this.toolbarConfig.showExtras = value > 0;
            if (value <= 0) {
                _this.timerService.extraTimer.stop();
                _this.timerService.gameTimer.start();
            }
        });
        this.timerService.gameTimer.timeObservable.subscribe(function (value) {
            if (_this.timerService.extraTimer.isActive()) {
                _this.timerService.gameTimer.stop();
            }
        });
    }
    GameViewComponent.prototype.processAnswer = function (answer) {
        alert(answer);
    };
    GameViewComponent = __decorate([
        core_1.Component({
            selector: 'game-view',
            templateUrl: 'app/views/game-view.component.html',
            styleUrls: ['app/views/game-view.component.css'],
            directives: [card_1.MD_CARD_DIRECTIVES, button_1.MD_BUTTON_DIRECTIVES, icon_1.MD_ICON_DIRECTIVES, icon_1.MD_ICON_DIRECTIVES,
                toolbar_component_1.ToolbarComponent, answer_buttons_component_1.AnswerButtonsComponent, mathjax_directive_1.MathJaxDirective]
        }), 
        __metadata('design:paramtypes', [timer_service_1.TimerService])
    ], GameViewComponent);
    return GameViewComponent;
}());
exports.GameViewComponent = GameViewComponent;
