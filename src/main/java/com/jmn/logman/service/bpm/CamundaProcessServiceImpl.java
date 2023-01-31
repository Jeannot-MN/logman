package com.jmn.logman.service.bpm;

import com.jmn.logman.model.User;
import com.jmn.logman.model.repository.UserRepository;
import com.jmn.logman.service.bpm.common.CamundaVariable;
import com.jmn.logman.service.bpm.common.ProcessOutcomes;
import com.jmn.logman.service.bpm.model.user.RegisterUserRequest;
import com.jmn.logman.service.bpm.model.user.RegisterUserResponse;
import com.jmn.logman.service.util.decision.DecisionTreeStopWatch;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;

@Service
public class CamundaProcessServiceImpl implements ProcessService {

    private static final Logger LOG = LoggerFactory.getLogger(CamundaProcessServiceImpl.class);

    private final RuntimeService appInMemoryRuntimeService;
    private final UserRepository userRepository;

    @Autowired
    public CamundaProcessServiceImpl(RuntimeService appInMemoryRuntimeService, UserRepository userRepository) {
        this.appInMemoryRuntimeService = appInMemoryRuntimeService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        Assert.notNull(request, "Request should not be null");

        String businessKey = request.getBusinessKey();
        LOG.info("businessKey: {}", businessKey);

        HashMap<String, Object> variables = new HashMap<>();
        variables.put(CamundaVariable.REQUEST.name(), request);

        DecisionTreeStopWatch decisionTreeStopWatch = new DecisionTreeStopWatch(businessKey);
        variables.put(CamundaVariable.PROCESS_DECISION_TREE.name(), decisionTreeStopWatch);
        variables.put(CamundaVariable.PROCESS_OUTCOMES.name(), new ProcessOutcomes());
//        variables.put(CamundaVariable.MANAGE_ROLES_REQUEST.name(), new ManageRolesRequest());

        decisionTreeStopWatch.start("Start");

        ProcessInstance processInstance = appInMemoryRuntimeService.startProcessInstanceByKey(request.getType().getId()
                , businessKey, variables);
        LOG.info("Completed <" + request.getType() + "> with process #: " + processInstance.getProcessInstanceId());

        User user = userRepository.findByUsername(request.getUsername());
        return new RegisterUserResponse(processInstance.getProcessInstanceId(), user);
    }


}
