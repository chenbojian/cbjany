import {Todo} from './todo';
import * as v4 from 'uuid/v4';
export class TodoService {
	todoList: Todo[];
	currentFilter: string;
	constructor() {
		this.todoList = [];
		this.currentFilter = 'All';
	}

	addTodo(newTodo: string) {
		this.todoList = [...this.todoList, {id: v4(), name: newTodo, completed: false,}];
	}

	get visibleTodoList() {
		if(this.currentFilter === 'All') {
			return this.todoList;
		}
		if(this.currentFilter === 'Active') {
			return this.todoList.filter(t => !t.completed);
		}
		if(this.currentFilter === 'Completed') {
			return this.todoList.filter(t => t.completed);
		}
		throw "argument error";
	}

	toggleTodo(todo: Todo, completed: boolean) {
		const index = this.todoList.indexOf(todo);

		this.todoList = [
			...this.todoList.slice(0, index),
			{
				id: todo.id,
				name: todo.name,
				completed
			},
			...this.todoList.slice(index + 1)
		];
	}

	delete(todo: Todo) {
		const index = this.todoList.indexOf(todo);
		this.todoList = [
			...this.todoList.slice(0, index),
			...this.todoList.slice(index + 1)
		];

	}

	update(todo: Todo, name: string) {
		const index = this.todoList.indexOf(todo);
		this.todoList = [
			...this.todoList.slice(0, index),
			{
				id: todo.id,
				name,
				completed: todo.completed
			}
			...this.todoList.slice(index + 1)
		];

	}


	filter(status) {
		this.currentFilter = status;
	}
}

