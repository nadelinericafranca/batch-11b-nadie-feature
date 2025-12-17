package xyz.mynt.bootcamp5.flag;

import io.flipt.api.FliptClient;
import io.flipt.api.evaluation.models.BooleanEvaluationResponse;
import io.flipt.api.evaluation.models.EvaluationRequest;
import org.springframework.web.context.request.RequestContextHolder;

public class VoucherFlagImpl implements VoucherFlag {

    private final FliptClient fliptClient;

    public VoucherFlagImpl(FliptClient fliptClient) {
        this.fliptClient = fliptClient;
    }

    @Override
    public boolean isUp(VoucherEndpoint endpoint) {
        // 1. Get the current sessionID, we will use this as entityID
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        // 2. Create evaluationRequest and pass the sessionId as entityId
        EvaluationRequest evaluationRequest = EvaluationRequest.builder()
                .namespaceKey(endpoint.getNamespace())
                .flagKey(endpoint.getKey())
                .entityId(sessionId)
                .build();

        // 3. Evaluate the request and get the response
        BooleanEvaluationResponse evaluationResponse = fliptClient.evaluation().evaluateBoolean(evaluationRequest);

        // 4. Return the response of the flag
        return evaluationResponse.isEnabled();
    }
}
