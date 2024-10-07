package com.gcash.exam.config;

import com.gcash.exam.entity.RuleEntity;
import com.gcash.exam.repository.RuleRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DroolsConfig {
    private final RuleRepository ruleRepository;

    @Bean
    public KieContainer kieContainer() throws UnsupportedEncodingException {
        KieServices ks = KieServices.Factory.get();

        KieFileSystem kfs = ks.newKieFileSystem();

        List<RuleEntity> rules = ruleRepository.findByEnabledTrue();
        String drl = buildDrl(rules);

        kfs.write("src/main/resources/rules/dynamicRules.drl",
                ResourceFactory.newByteArrayResource(drl.getBytes("UTF-8")));

        KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll();

        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("Error building KieBase: " + kb.getResults().toString());
        }

        return ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
    }

    private String buildDrl(List<RuleEntity> rules) {
        StringBuilder drl = new StringBuilder();
        drl.append("package com.gcash.exam.rules;\n\n");
        drl.append("import com.gcash.exam.dto.ParcelRequestDTO;\n");
        drl.append("import com.gcash.exam.dto.ParcelResponseDTO;\n\n");
        drl.append("global com.gcash.exam.dto.ParcelResponseDTO response;\n\n");

        for (RuleEntity rule : rules) {
            drl.append("rule \"").append(rule.getName()).append("\"\n");
            drl.append("salience ").append(rule.getSalience()).append("\n");
            drl.append("when\n");
            drl.append("    ").append(rule.getCondition()).append("\n");
            drl.append("then\n");
            drl.append("    ").append(rule.getAction()).append("\n");
            drl.append("end\n\n");
        }

        return drl.toString();
    }
}
