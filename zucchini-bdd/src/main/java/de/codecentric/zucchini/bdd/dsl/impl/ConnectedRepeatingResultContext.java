/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.codecentric.zucchini.bdd.dsl.impl;

import de.codecentric.zucchini.bdd.dsl.Fact;
import de.codecentric.zucchini.bdd.dsl.RepeatingResultContext;
import de.codecentric.zucchini.bdd.dsl.Result;
import de.codecentric.zucchini.bdd.dsl.Step;
import de.codecentric.zucchini.bdd.resolver.StatementResolverHolder;

import java.util.List;

public class ConnectedRepeatingResultContext extends ConnectedTermination implements RepeatingResultContext {
	private final List<Result> results;

	public ConnectedRepeatingResultContext(List<Fact> facts, List<Step> steps, List<Result> results) {
		super(facts, steps, results);
		this.results = results;
	}

	@Override
	public RepeatingResultContext andThen(Result result) {
		results.add(result);
		return this;
	}

	@Override
	public RepeatingResultContext andThen(String resultName) {
		return andThen(StatementResolverHolder.getStatementResolver().resolveStatement(resultName, Result.class));
	}
}