import {TodoService} from './todo-service';

class VisibleTodoItemsController {
	static get $inject() {
		return ['todoService'];
	}
	constructor(private todoService: TodoService) {
	}

	get todos() {
		return this.todoService.visibleTodoList;
	}

}

export const VisibleTodoItemsComponent = {
	templateUrl: 'app/visible-todo-items.html',
	controller: VisibleTodoItemsController,
	bindings: {},
};