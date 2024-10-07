package com.gcash.exam.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RuleEntityTest {

    @Test
    void ruleEntity_shouldHaveCorrectFieldValues() {
        RuleEntity rule = new RuleEntity();
        rule.setId(1);
        rule.setName("Reject Parcel");
        rule.setSalience(100);
        rule.setCondition("$request : ParcelRequest( weight > 50 )");
        rule.setAction("response.setRuleApplied(\"Reject\"); response.setCost(0); response.setMessage(\"Weight exceeds the maximum limit of 50kg.\"); drools.halt();");
        rule.setEnabled(true);
        rule.setDescription("Reject parcels over 50kg");

        assertEquals(1, rule.getId());
        assertEquals("Reject Parcel", rule.getName());
        assertEquals(100, rule.getSalience());
        assertEquals("$request : ParcelRequest( weight > 50 )", rule.getCondition());
        assertEquals("response.setRuleApplied(\"Reject\"); response.setCost(0); response.setMessage(\"Weight exceeds the maximum limit of 50kg.\"); drools.halt();", rule.getAction());
        assertTrue(rule.getEnabled());
        assertEquals("Reject parcels over 50kg", rule.getDescription());
    }

    @Test
    void ruleEntity_shouldHandleNullValues() {
        RuleEntity rule = new RuleEntity();
        rule.setId(null);
        rule.setName(null);
        rule.setSalience(null);
        rule.setCondition(null);
        rule.setAction(null);
        rule.setEnabled(null);
        rule.setDescription(null);

        assertNull(rule.getId());
        assertNull(rule.getName());
        assertNull(rule.getSalience());
        assertNull(rule.getCondition());
        assertNull(rule.getAction());
        assertNull(rule.getEnabled());
        assertNull(rule.getDescription());
    }

    @Test
    void ruleEntity_shouldHandleEmptyStrings() {
        RuleEntity rule = new RuleEntity();
        rule.setName("");
        rule.setCondition("");
        rule.setAction("");
        rule.setDescription("");

        assertEquals("", rule.getName());
        assertEquals("", rule.getCondition());
        assertEquals("", rule.getAction());
        assertEquals("", rule.getDescription());
    }
}
