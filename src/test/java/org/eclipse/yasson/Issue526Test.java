/*
 * Copyright (c) 2024 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

package org.eclipse.yasson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Set;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;

public class Issue526Test {

    @Test
    public void shouldInvokeAdapterOnObjectInCollection() {
        var json = JsonbBuilder.create().toJson(Set.of(new C()));
        assertEquals("[\"correct value\"]", json);
    }

    @JsonbTypeAdapter(CAdapter.class)
    public static class C {
        public String wrong = "value";
    }

    public static class CAdapter implements JsonbAdapter<C, String> {

        @Override
        public String adaptToJson(C obj) {
            return "correct value";
        }

        @Override
        public C adaptFromJson(String obj) {
            throw new UnsupportedOperationException();
        }
    }
}
