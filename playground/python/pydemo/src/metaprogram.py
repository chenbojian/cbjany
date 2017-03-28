
def make_hook(f):
    """Decorator to turn 'foo' method into '__foo__'"""
    f.is_hook = 1
    return f


class MyType(type):
    def __new__(mcs, name, bases, attrs):
        if name.startswith('None'):
            return None

        # Go over attributes and see if they should be renamed.
        newattrs = {}
        for attrname, attrvalue in attrs.items():
            if getattr(attrvalue, 'is_hook', 0):
                newattrs['__%s__' % attrname] = attrvalue
            else:
                newattrs[attrname] = attrvalue

        return super(MyType, mcs).__new__(mcs, name, bases, newattrs)

    def __init__(cls, name, bases, attrs):
        super(MyType, cls).__init__(name, bases, attrs)

        # classregistry.register(self, self.interfaces)
        print("Would register class %s now." % cls)

    def __add__(cls, other):
        class AutoClass(cls, other):
            pass

        return AutoClass

    def unregister(cls):
        # classregistry.unregister(self)
        print("Would unregister class %s now." % cls)


class MyObject(metaclass=MyType):
    pass


class NoneSample(MyObject):
    pass

# Will print "NoneType None"
print(type(NoneSample), repr(NoneSample))


class Example(MyObject):
    def __init__(self, value):
        self.value = value

    @make_hook
    def add(self, other):
        return self.__class__(self.value + other.value)


# Will unregister the class
Example.unregister()

inst = Example(10)
# Will fail with an AttributeError
# inst.unregister()

print(inst + inst)


class Sibling(MyObject):
    pass


ExampleSibling = Example + Sibling
# ExampleSibling is now a subclass of both Example and Sibling (with no
# content of its own) although it will believe it's called 'AutoClass'
print(ExampleSibling)
print(ExampleSibling.__mro__)
