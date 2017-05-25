import {TodoService} from './todo-service';

class TodoHeaderController {
	value:string;

	static get $inject (){
		return ['todoService'];
	}

	constructor(private todoService: TodoService) {
	}

	$onInit() {
		this.value = "";
	}

	onSubmit() {
		this.todoService.addTodo(this.value);
		this.value = "";
	}
}


export const TodoHeaderComponent = {
	templateUrl: 'app/todo-header.html',
	controller: TodoHeaderController,
	bindings: {}
}
