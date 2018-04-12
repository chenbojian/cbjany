import angular from 'angular';
import { rootComponent } from './root.component';
import { components } from './components/components.module';

export default angular.module('root', [components])
    .component('root', rootComponent)
    .name;
