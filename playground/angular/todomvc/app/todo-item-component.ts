import {Todo} from './todo';
import {TodoService} from './todo-service';

class TodoItemController {
	item: Todo;
	completed: boolean;
	editing: boolean;
	value: string;
	static get $inject() {
		return ['todoService'];
	}
	constructor(private todoService: TodoService) {
		this.editing = false;
	}

	$onInit() {
		this.completed = this.item && this.item.completed;
	}

	onToggle() {
		this.todoService.toggleTodo(this.item, this.completed);
	}

	onDelete() {
		this.todoService.delete(this.item);
	}

	edit() {
		this.editing = true;
		this.value = this.item.name;
	}

	exitEdit(source: string) {
		this.editing = false;
		if(source === 'submit') {
			this.todoService.update(this.item, this.value);
		}
	}
}

export const TodoItemComponent = {
	templateUrl: 'app/todo-item.html',
	controller: TodoItemController,
	bindings: {
		item: '<'
	}
}