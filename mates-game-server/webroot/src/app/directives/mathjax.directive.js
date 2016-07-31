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
var MathJaxDirective = (function () {
    function MathJaxDirective(el, appRef) {
        this.el = el;
        this.appRef = appRef;
    }
    MathJaxDirective.prototype.ngOnChanges = function () {
        console.log(MathJax);
        this.el.nativeElement.innerHTML = this.texExpression;
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, this.el.nativeElement]);
    };
    __decorate([
        core_1.Input('MathJax'), 
        __metadata('design:type', String)
    ], MathJaxDirective.prototype, "texExpression", void 0);
    MathJaxDirective = __decorate([
        core_1.Directive({
            selector: '[MathJax]'
        }), 
        __metadata('design:paramtypes', [core_1.ElementRef, core_1.ApplicationRef])
    ], MathJaxDirective);
    return MathJaxDirective;
}());
exports.MathJaxDirective = MathJaxDirective;
