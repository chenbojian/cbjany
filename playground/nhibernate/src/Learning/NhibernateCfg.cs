using System.Reflection;
using NHibernate;
using NHibernate.Cfg;
using NHibernate.Cfg.MappingSchema;
using NHibernate.Mapping.ByCode;
using NHibernate.Tool.hbm2ddl;

namespace Learning
{
    public class NhibernateCfg
    {
        private static ISessionFactory _sessionFactory;

        public static ISessionFactory CreateSessionFactory()
        {
            var configuration = new Configuration();
            configuration.DataBaseIntegration(db =>
            {
                db.Dialect<NHibernate.Dialect.MsSql2012Dialect>();
                db.Driver<NHibernate.Driver.SqlClientDriver>();
                db.ConnectionString = "Server=.;Database=cbj;Trusted_Connection=True;";
            });

            configuration.AddMapping(GetMapping());

            new SchemaExport(configuration).Execute(false, true, false);

            _sessionFactory = configuration.BuildSessionFactory();

            return _sessionFactory;
        }

        public static HbmMapping GetMapping()
        {
            var modelMapper = new ModelMapper();
            modelMapper.AddMappings(Assembly.GetAssembly(typeof(PeopleMap)).GetExportedTypes());
            HbmMapping mapping = modelMapper.CompileMappingForAllExplicitlyAddedEntities();
            return mapping;
        }

        public static ISession OpenSession()
        {
            if (_sessionFactory == null)
            {
                return CreateSessionFactory().OpenSession();
            }
            return _sessionFactory.OpenSession();
        }
    }
}