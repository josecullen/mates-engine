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
var http_1 = require('@angular/http');
var card_1 = require('@angular2-material/card');
var button_1 = require('@angular2-material/button');
var icon_1 = require('@angular2-material/icon');
var toolbar_component_1 = require('./components/toolbar.component');
var toolbar_demo_component_1 = require('./views/toolbar-demo.component');
var game_view_component_1 = require('./views/game-view.component');
var timer_service_1 = require('./services/timer.service');
var GameMatesAppComponent = (function () {
    function GameMatesAppComponent(mdIconRegistry, timerService) {
        this.mdIconRegistry = mdIconRegistry;
        this.timerService = timerService;
        this.title = 'game-mates works!';
        mdIconRegistry
            .addSvgIcon('thumb-up', '/game-mates/icon/assets/thumbup-icon.svg')
            .addSvgIconSetInNamespace('core', '/game-mates/icon/assets/core-icon-set.svg')
            .registerFontClassAlias('fontawesome', 'fa');
        timerService.gameTimer.start();
    }
    ;
    GameMatesAppComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'game-mates-app',
            templateUrl: 'game-mates.component.html',
            styleUrls: ['game-mates.component.css'],
            directives: [card_1.MD_CARD_DIRECTIVES, button_1.MD_BUTTON_DIRECTIVES, icon_1.MD_ICON_DIRECTIVES, icon_1.MD_ICON_DIRECTIVES,
                toolbar_component_1.ToolbarComponent, toolbar_demo_component_1.ToolbarDemoComponent, game_view_component_1.GameViewComponent],
            providers: [http_1.HTTP_PROVIDERS, timer_service_1.TimerService],
            viewProviders: [icon_1.MdIconRegistry]
        }), 
        __metadata('design:paramtypes', [icon_1.MdIconRegistry, timer_service_1.TimerService])
    ], GameMatesAppComponent);
    return GameMatesAppComponent;
}());
exports.GameMatesAppComponent = GameMatesAppComponent;
