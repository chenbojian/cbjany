import unittest
import random


class Demo(unittest.TestCase):
    def test_first(self):
        self.assertEqual(3, 3)

    def test_graph(self):
        import graph
        g = graph.Graph()
        g.vertex.add(3)
        self.assertEqual(len(g), 1)

    def test_relative_import(self):
        from mod.submod2 import submodule2
        self.assertEqual(submodule2, 10)

    def test_class(self):
        class A(object):
            count = 0

            def __new__(cls):
                setattr(cls, 'hello{0}'.format(cls.count), random.randint(1,100))
                cls.count += 1
                return super(A, cls).__new__(cls)

            def __init__(self): pass
        A()
        self.assertTrue(hasattr(A, 'hello0'))
        A()
        self.assertTrue(hasattr(A, 'hello1'))
