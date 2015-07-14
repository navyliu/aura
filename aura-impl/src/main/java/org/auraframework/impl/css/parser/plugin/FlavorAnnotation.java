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
package org.auraframework.impl.css.parser.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.salesforce.omakase.ast.Comment;

/**
 * Represents a CSS annotation (comment) with metadata about a flavor.
 * <p>
 * The annotation starts with the name of the flavor, then contains comma-separated key-value pairs. Each key-value pair
 * is separate by a space. For example:
 *
 * <pre>
 * <code>
 * &#8725;* &#64;flavor foo, extends default *&#8725;
 * .THIS--foo {}
 * </code>
 * </pre>
 *
 * Available key-value params: <br>
 * <ul>
 * <li>extends <i>name</i></li>
 */
final class FlavorAnnotation {
    private static final String NAME = "@flavor";

    private final String flavorName;
    private final Optional<String> optionExtends;

    public FlavorAnnotation(Map<String, String> map) {
        this.flavorName = checkNotNull(map.get("flavor"), "flavorName cannot be null");
        this.optionExtends = Optional.fromNullable(map.get("extends"));
    }

    /** gets the name of the flavor */
    public String getFlavorName() {
        return flavorName;
    }

    /** optional extends param */
    public Optional<String> getExtends() {
        return optionExtends;
    }

    /** returns a {@link FlavorAnnotation} object if the given CSS comment contains one */
    public static Optional<FlavorAnnotation> find(Comment comment) {
        String string = comment.content().trim();
        if (string.startsWith(NAME)) {
            string = string.substring(1); // remove the @ sign
            Map<String, String> map = Splitter.on(",").trimResults().omitEmptyStrings().withKeyValueSeparator(" ").split(string);
            return Optional.of(new FlavorAnnotation(map));
        }
        return Optional.absent();
    }
}