var Cat = require('../src/cat.js');

describe('something', () => {
    it('should be true', () => {
        expect(true).toBe(true);
    });

    it('should be cat', () => {
        var cat = new Cat(); 
        expect(cat.name).toEqual('name');
    });
});
