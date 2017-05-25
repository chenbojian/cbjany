import * as angular from 'angular';
import {TodoHeaderComponent} from './todo-header-component';
import {VisibleTodoItemsComponent} from './visible-todo-items-component';
import {TodoItemComponent} from './todo-item-component';
import {TodoFooterComponent} from './todo-footer-component';
import {TodoService} from './todo-service';

angular.module('app', []);

angular.module('app')
	.component('main', {
		templateUrl: 'app/app.html'
	})
	.component('todoHeader', TodoHeaderComponent)
	.component('visibleTodoItems', VisibleTodoItemsComponent)
	.component('todoItem', TodoItemComponent)
	.component('todoFooter', TodoFooterComponent)
	.service('todoService', TodoService);