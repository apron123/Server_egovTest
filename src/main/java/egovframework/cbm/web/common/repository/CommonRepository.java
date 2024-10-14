package egovframework.cbm.web.common.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommonRepository<T> {

    private final JPAQueryFactory queryFactory;

    public Page<T> findByFilters(EntityPathBase<T> qEntity, BooleanExpression whereQuery, Pageable pageable,
                                 EntityPathBase<?>... joinEntityPaths) {

        JPAQuery<T> query = joinQuery(qEntity, joinEntityPaths);

        List<T> entityList = query
                .where(whereQuery)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(Utils.getOrderSpecifier(pageable.getSort(), qEntity))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(qEntity.count())
                .from(qEntity)
                .where(whereQuery);

        return PageableExecutionUtils.getPage(entityList, pageable, countQuery::fetchOne);
    }

    private JPAQuery<T> joinQuery(EntityPathBase<T> qEntity, EntityPathBase<?>... joinEntityPaths) {
        JPAQuery<T> query = queryFactory.selectFrom(qEntity);
        for (EntityPathBase<?> joinEntityPath : joinEntityPaths) {
            query.leftJoin(joinEntityPath).fetchJoin();
        }
        return query;
    }

}
