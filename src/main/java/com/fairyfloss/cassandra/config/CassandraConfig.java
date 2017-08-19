package com.fairyfloss.cassandra.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.DefaultNamingStrategy;
import com.datastax.driver.mapping.DefaultPropertyMapper;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingConfiguration;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.NamingConventions;
import com.datastax.driver.mapping.PropertyMapper;
import com.fairyfloss.cassandra.accessor.GoodsAccessor;
import com.fairyfloss.cassandra.domain.GoodsDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fairyfloss.cassandra.constant.CassandraConstant.DEMANDED_SIZE;

/**
 * Created by Lindsay on 2017/5/1.
 */

@Configuration
public class CassandraConfig {

    private Session session;

    CassandraConfig() {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
        queryOptions.setFetchSize(DEMANDED_SIZE);
        queryOptions.setDefaultIdempotence(true);


        Cluster cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .withQueryOptions(queryOptions)
                .withProtocolVersion(ProtocolVersion.V4) // better to clarify it explictly for it will be set due to the first host connected.
//                .withCredentials("root", "root")
                .build();
        this.session = cluster.connect("demo");
    }

    @Bean
    public Session session() {
        return session;
    }

    @Bean
    public MappingManager mappingManager(Session session) {
        MappingConfiguration mappingConfiguration = getMappingConfiguration();
        return new MappingManager(session, mappingConfiguration);
    }

    private MappingConfiguration getMappingConfiguration() {
        PropertyMapper propertyMapper = new DefaultPropertyMapper().setNamingStrategy(
                new DefaultNamingStrategy(
                        NamingConventions.LOWER_CAMEL_CASE,
                        NamingConventions.LOWER_SNAKE_CASE
                ));
        return MappingConfiguration.builder().withPropertyMapper(propertyMapper).build();
    }

    @Bean
    public Mapper<GoodsDO> goodsMapper(MappingManager mappingManager) {
        return mappingManager.mapper(GoodsDO.class);
    }


    @Bean
    public GoodsAccessor goodsAccessor(MappingManager mappingManager) {
        return mappingManager.createAccessor(GoodsAccessor.class);
    }
}
