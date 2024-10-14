package egovframework.cbm.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class QueryDslConfig {

    @PersistenceContext(unitName = DatabaseConstants.BaseDatabase.entity_manager_factory)
    private EntityManager baseEntityManager;

    @Primary
    @Bean
    public JPAQueryFactory baseJpaQueryFactory() {
        return new JPAQueryFactory(baseEntityManager);
    }

}
