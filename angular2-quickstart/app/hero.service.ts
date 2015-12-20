import {Injectable} from 'angular2/core';
import {HEROES} from './mock-heroes';

@Injectable()
export class HeroService{
    getHeroes() {
        return new Promise(resolve => setTimeout(() => resolve(HEROES), 2000));
    }
}
