package com.baidu.asf.engine.command;

import com.baidu.asf.engine.ProcessorContext;
import com.baidu.asf.persistence.CachedEntityManagerHandler;
import com.baidu.asf.persistence.EntityManager;

/**
 * Replace the origin EntityManager to cache manager
 */
public class CacheCommandInterceptor extends AbstractCommandInterceptor {

    @Override
    public <T> T execute(ProcessorContext context, Command<T> command) {
        final EntityManager entityManager = context.getEntityManager();
        final boolean canCache = !Boolean.valueOf((String) context.getParam(ProcessorContext.ParamKeys.DisabledCache
                .paramName));
        if (canCache && entityManager != null && !(entityManager instanceof
                CachedEntityManagerHandler)) {
            context.setEntityManager(new CachedEntityManagerHandler(entityManager).getProxy());
        }
        try {
            return next.execute(context, command);
        } finally {
            // restore
            if (canCache && entityManager != null && entityManager != context.getEntityManager()
                    ) {
                context.setEntityManager(entityManager);
            }
        }
    }
}