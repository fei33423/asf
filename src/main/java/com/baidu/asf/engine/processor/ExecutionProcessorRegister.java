package com.baidu.asf.engine.processor;

import com.baidu.asf.model.ActType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguoqing01 on 14-3-14.
 */
public class ExecutionProcessorRegister {
    private static final Map<ActType, ExecutionProcessor> processors = new HashMap<ActType, ExecutionProcessor>();

    // register processors for all act nodeId
    static {
        processors.put(ActType.UserTask, new UserTaskProcessor());
        processors.put(ActType.ServiceTask, new ServiceTaskProcessor());
        processors.put(ActType.ParallelGateway, new ParallelGatewayProcessor());
        processors.put(ActType.ExclusiveGateway, new ExclusiveGatewayProcessor());
        processors.put(ActType.InclusiveGateway, new InclusiveGatewayProcessor());
        processors.put(ActType.SubProcess, new DefaultSubProcessProcessor());
        processors.put(ActType.EndEvent, new EndEventProcessor());
    }

    public static ExecutionProcessor getProcessor(ActType actType) {
        return processors.get(actType);
    }
}