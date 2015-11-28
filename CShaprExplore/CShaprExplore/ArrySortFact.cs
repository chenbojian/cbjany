using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;
using Xunit.Abstractions;

namespace CShaprExplore
{
    public class ArraySortFact
    {
        private readonly ITestOutputHelper output;
        public ArraySortFact(ITestOutputHelper output)
        {
            this.output = output;
        }

        [Fact]
        public void test_sort_using_class_implements_icomparer()
        {
            MyArrayClass[] array = { new MyArrayClass(3), new MyArrayClass(1), new MyArrayClass(2) };
            Array.Sort(array, new CompareA());
            output.WriteLine("==============");
            Assert.Equal(1, array[0].Value);
        }

        [Fact]
        public void test_sort_using_delegate()
        {
            MyArrayClass[] array = { new MyArrayClass(3), new MyArrayClass(1), new MyArrayClass(2) };
            Array.Sort(array, CompareMyArray);
            Console.WriteLine("hello");
            Assert.Equal(1, array[0].Value);
        }

        private static int CompareMyArray(MyArrayClass x, MyArrayClass y)
        {
            if (x.Value > y.Value)
            {
                return 1;
            }
            if (x.Value < y.Value)
            {
                return -1;
            }
            return 0;
        }
    }

    internal class CompareA : IComparer<MyArrayClass>
    {
        public int Compare(MyArrayClass x, MyArrayClass y)
        {
            if (x.Value > y.Value)
            {
                return 1;
            }
            else if(x.Value < y.Value) {
                return -1;
            }
            return 0;
        }
    }
    class MyArrayClass
    {
        public MyArrayClass(int value)
        {
            Value = value;
        }
        public int Value;
    }
}
