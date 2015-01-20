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
package org.auraframework.def;

import org.auraframework.Aura;
import org.auraframework.throwable.quickfix.InvalidDefinitionException;

public class ComponentDefTest extends BaseComponentDefTest<ComponentDef> {

    public ComponentDefTest(String name) {
        super(name, ComponentDef.class, "aura:component");
    }

    /**
     * InvalidDefinitionException if we try to instantiate an abstract component with no providers.
     */
    public void testAbstractNoProvider() throws Exception {
        try {
            ComponentDef cd = define(baseTag, "abstract='true'", "");
            Aura.getInstanceService().getInstance(cd);
            fail("Should not be able to instantiate a component with no providers.");
        } catch (Exception e) {
            checkExceptionContains(e, InvalidDefinitionException.class, "cannot be instantiated directly");
        }
    }
}