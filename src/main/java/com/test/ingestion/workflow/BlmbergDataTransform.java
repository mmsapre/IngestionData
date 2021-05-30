package com.test.ingestion.workflow;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.ingestion.model.BloombergDTO;
import com.test.ingestion.model.Record;
import com.test.ingestion.service.InternalIdService;
import com.test.ingestion.utils.Constants;
import org.apache.camel.Exchange;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BlmbergDataTransform extends AbstractBaseProcessor implements Serializable {


    private final String workflowName;
    private final String transformStep;
    private final InternalIdService internalIdService;

    public BlmbergDataTransform(InternalIdService internalIdService, String workflowName, String transformStep) {
        this.workflowName = workflowName;
        this.transformStep = transformStep;
        this.internalIdService = internalIdService;
    }

    @Override
    public void process(Exchange exchange) throws IOException {
        String distributionEvent = exchange.getIn().getBody(String.class);
        final Map<String, Object> hdrMap = (Map<String, Object>) exchange.getIn().getHeaders().get(Constants.HEADER);
        List<Object> blmergFilterSpecs = JsonUtils.classpathToList("/blmbergmapper.json");
        Chainr chainr = Chainr.fromSpec(blmergFilterSpecs);

        Object distributionInputJSON = JsonUtils.jsonToObject(distributionEvent);
        Object distributionOutputJSON = chainr.transform(distributionInputJSON);
        String distributionJsonString = JsonUtils.toJsonString(distributionOutputJSON);
        ObjectMapper objectMapper = new ObjectMapper();
        BloombergDTO bloombergDTO = objectMapper.readValue(distributionJsonString, BloombergDTO.class);
        String secId = internalIdService.bankEquityId(bloombergDTO.getProviderId());
        bloombergDTO.setInternalId(secId);
        hdrMap.put(Constants.TRFM_BLMBERG, bloombergDTO);
        String transformDTO = objectMapper.writeValueAsString(bloombergDTO);
        Map map = objectMapper.readValue(transformDTO, Map.class);
        Record record = new Record();
        record.setPayload(map);
        record.setFileId(bloombergDTO.getFileId());
        exchange.getOut().setHeader(Constants.HEADER, hdrMap);
        exchange.getOut().setHeader(Constants.RESULT, record);
        setCurrentStepAndResult(exchange, workflowName, this.transformStep, Constants.SUCCESS);
    }
}
