/*
 * Copyright (C) 2016 Stefan Henß
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.insightml;

import java.io.Serializable;

import org.apache.commons.math3.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.insightml.data.IDataset;
import com.insightml.data.samples.SimpleSample;
import com.insightml.data.samples.Sample;
import com.insightml.evaluation.functions.Accuracy;
import com.insightml.evaluation.functions.ObjectiveFunction;
import com.insightml.evaluation.functions.LogLoss;
import com.insightml.evaluation.functions.RMSE;
import com.insightml.math.distributions.IDiscreteDistribution;
import com.insightml.models.ILearner;

public abstract class AbstractModelTest {

	@Test
	public final void testNumeric() {
		test(getNumeric(), TestDatasets.createNumeric(), new RMSE());
	}

	@Test
	public final void testBoolean() {
		test(getBoolean(), TestDatasets.createBoolean(), new LogLoss(false));
	}

	@Test
	public final void testNominal() {
		test(getNominal(), TestDatasets.createNominal(), new Accuracy(0.5));
	}

	private static void test(final Pair<? extends ILearner, Double> tuple,
			final IDataset<SimpleSample, Double, ?> instances,
			final ObjectiveFunction<? super SimpleSample, ? super Serializable> objective) {
		if (tuple != null) {
			final double expected = tuple.getSecond();
			Assert.assertTrue(expected + " > -0.82", expected > -0.82);
			Tests.testLearner(tuple.getFirst(), instances, objective, expected);
		}
	}

	protected abstract Pair<? extends ILearner<Sample, ? super Double, Double>, Double> getNumeric();

	protected abstract Pair<? extends ILearner<Sample, ? super Boolean, Double>, Double> getBoolean();

	protected abstract Pair<? extends ILearner<Sample, String, IDiscreteDistribution<String>>, Double> getNominal();
}
