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

package de.codecentric.zucchini.bdd;

import org.junit.Test;
import org.mockito.ArgumentMatcher;

import static de.codecentric.zucchini.bdd.dsl.impl.ScenarioBuilder.given;
import static de.codecentric.zucchini.bdd.dsl.impl.facts.Facts.noContext;
import static de.codecentric.zucchini.bdd.dsl.impl.results.Results.noResult;
import static de.codecentric.zucchini.bdd.dsl.impl.steps.Steps.noOperation;
import static org.mockito.Mockito.*;

public class ExecutionContextTest {
	@Test
	public void testOneFactTwoStepsOneResultZeroExamples() {
		Executor executorMock = mock(Executor.class);
		ExecutorHolder.setExecutor(executorMock);

		given(noContext())
				.when(noOperation())
				.andWhen(noOperation())
				.then(noResult())
				.end();

		verifyExecutionContext(executorMock, 1, 2, 1);
	}

	@Test
	public void testOneFactOneStepOneResultZeroExamples() {
		Executor executorMock = mock(Executor.class);
		ExecutorHolder.setExecutor(executorMock);

		given(noContext())
				.when(noOperation())
				.then(noResult())
				.end();

		verifyExecutionContext(executorMock, 1, 1, 1);
	}

	@Test
	public void testOneFactOneStepThreeResults() {
		Executor executorMock = mock(Executor.class);
		ExecutorHolder.setExecutor(executorMock);

		given(noContext())
				.when(noOperation())
				.then(noResult())
				.andThen(noResult())
				.andThen(noResult())
				.end();

		verifyExecutionContext(executorMock, 1, 1, 3);
	}

	@SuppressWarnings({"SameParameterValue", "UnusedParameters"})
	private void verifyExecutionContext(Executor executorMock, final int expectedNumberOfFacts, final int expectedNumberOfSteps, final int expectedNumberOfResults) {
		verify(executorMock).execute(argThat(new ArgumentMatcher<ExecutionContext>() {
			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof ExecutionContext)) {
					return false;
				}
				ExecutionContext executionContext = (ExecutionContext) argument;
				return executionContext.getFacts().size() == 1
						&& executionContext.getSteps().size() == expectedNumberOfSteps
						&& executionContext.getResults().size() == expectedNumberOfResults;
			}
		}));
		verifyNoMoreInteractions(executorMock);
	}
}
