var Cat = require('../src/cat.js');

describe('something', () => {
    it('should be true', () => {
        expect(true).toBe(true);
    });

    it('should be cat', () => {
        var cat = new Cat(); 
        spyOn(cat, 'getName').and.returnValue('cat');
        expect(cat.getName()).toEqual('cat');
    });

    it('should be another cat', () => {
        var cat = new Cat(); 
        spyOn(cat, 'getName').and.callThrough();

        expect(cat.getName()).toEqual('name');

        expect(cat.getName).toHaveBeenCalled();

        cat.getName.and.returnValue('another cat');
        expect(cat.getName()).toEqual('another cat');
    });
});
