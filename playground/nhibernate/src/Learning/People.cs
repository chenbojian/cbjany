using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Learning
{
    public class People
    {
        public virtual long Id { get; set; }
        public virtual string Name { get; set; }
    }

    public class PeopleMap : ClassMapping<People>
    {
        public PeopleMap()
        {
            Table("people");
            Id(p => p.Id, m =>
            {
                m.Column("id");
                m.Generator(new IdentityGeneratorDef());
            });
            Property(p => p.Name, m =>
            {
                m.Column("name");
            });
        }
    }
}