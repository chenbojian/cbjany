import {TodoService} from './todo-service';

class TodoFooterController {
	static get $inject() {
		return ['todoService'];
	}

	constructor(private todoService: TodoService) {}

	get todoCount() {
		return this.todoService.todoList.filter(todo => !todo.completed).length;
	}

	updateFilter(status) {
		this.todoService.filter(status);
	}

	get currentFilter() {
		return this.todoService.currentFilter;
	}
}

export const TodoFooterComponent = {
	templateUrl: 'app/todo-footer.html',
	controller: TodoFooterController,
};