/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.throwable.quickfix;

import java.util.Map;

import org.auraframework.Aura;
import org.auraframework.def.ComponentDef;
import org.auraframework.def.DefDescriptor;
import org.auraframework.def.TokensDef;
import org.auraframework.service.BuilderService;
import org.auraframework.service.DefinitionService;

import com.google.common.collect.Maps;

/**
 * Quick fix for creating a missing {@link TokensDef}.
 */
public class CreateTokenDefQuickFix extends AuraQuickFix {

    public CreateTokenDefQuickFix(Map<String, Object> attributes) {
        super("Create Tokens Definition", attributes, Aura.getDefinitionService().getDefDescriptor(
                "auradev:createTokensDefQuickFix", ComponentDef.class));
    }

    public CreateTokenDefQuickFix(DefDescriptor<?> descriptor) {
        this(createMap(descriptor));
    }

    private static Map<String, Object> createMap(DefDescriptor<?> descriptor) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("descriptor", descriptor);
        return ret;
    }

    @Override
    protected void fix() throws Exception {
        BuilderService builderService = Aura.getBuilderService();
        DefinitionService definitionService = Aura.getDefinitionService();
        String descriptors = (String) getAttributes().get("descriptor");
        String[] split = CreateComponentDefQuickFix.descriptorPattern.split(descriptors);
        for (String descriptor : split) {
            TokensDef def = builderService.getTokensDefBuilder().setDescriptor(descriptor).build();
            definitionService.save(def);
            resetCache(def.getDescriptor());
        }
    }

}
