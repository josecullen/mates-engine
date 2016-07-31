import {Component, Input, Output, EventEmitter} from '@angular/core';
import {NgClass} from '@angular/common';
import {HTTP_PROVIDERS} from '@angular/http';
import { MD_CARD_DIRECTIVES } from '@angular2-material/card';
import { MD_BUTTON_DIRECTIVES } from '@angular2-material/button';
import { MD_GRID_LIST_DIRECTIVES } from '@angular2-material/grid-list';
import { MD_ICON_DIRECTIVES,MdIconRegistry,MdIcon} from '@angular2-material/icon';
import { MdToolbar } from '@angular2-material/toolbar';
import { Observable } from "rxjs/Observable";
import {CounterComponent, CounterConfig} from './counter.component';

@Component({
  selector: 'answer-buttons',
  templateUrl: 'app/components/answer-buttons.component.html',
  styleUrls: ['app/components/answer-buttons.component.css'],
  directives: [MD_CARD_DIRECTIVES, MD_BUTTON_DIRECTIVES, MD_ICON_DIRECTIVES, MD_GRID_LIST_DIRECTIVES, MdIcon, MdToolbar,
    CounterComponent, NgClass],
  providers: [HTTP_PROVIDERS],
  viewProviders: [MdIconRegistry]

})
export class AnswerButtonsComponent {
  @Input() answers:Array<string> = new Array();
  @Output() answer:EventEmitter<string> = new EventEmitter<string>();
  constructor(){ };

  sendAnswer(answer:string){
    this.answer.emit(answer);
  }

}
