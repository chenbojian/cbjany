using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NHibernate.Cfg;
using NHibernate.Linq;
using NHibernate.Mapping.ByCode;
using Xunit;
using Xunit.Abstractions;

namespace Learning.Test
{
    public class TestMapping
    {
        private ITestOutputHelper _output;
        public TestMapping(ITestOutputHelper output)
        {
            this._output = output;
        }

        [Fact]
        public void FirstTest()
        {
            var session = NhibernateCfg.OpenSession();

            session.Save(new People
            {
                Name = "cbj"
            });
            session.Flush();

            var people = session.Query<People>().First();
            Assert.Equal("cbj", people.Name);
        }
    }
}
